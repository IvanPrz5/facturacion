package com.ceag.facturacion.Service.Facturacion;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.ceag.facturacion.Dto.Xml.RespuestaTimbrado;
import com.ceag.facturacion.Entity.Facturacion.ComprobanteEntity;
import com.ceag.facturacion.Repository.Facturacion.ComprobanteRepository;
import com.ceag.facturacion.Utils.DatosFactura.DatosCancelacion;
import com.ceag.facturacion.Utils.DatosFactura.DatosFactura;
import com.ceag.facturacion.Utils.DatosFacturacion.DatosFacturacionCeag;
import com.ceag.facturacion.Utils.DatosFacturacion.FacturacionCeagStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Service
public class SwXmlService {
    
    @Autowired
    ComprobanteRepository comprobanteRepository;

    @Autowired
    ComprobanteService comprobanteService;

    private DatosFacturacionCeag datosFacturacionCeag;

    public RespuestaTimbrado timbrarXml(String route, DatosFactura datosFactura){
        RespuestaTimbrado respuestaTDto = new RespuestaTimbrado();
        datosFacturacionCeag = new DatosFacturacionCeag(FacturacionCeagStatus.TIPO_PRODUCCION);
        
        try {
            String token = getToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            
            File file = new File(route + "guardado.xml");
            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("xml", new FileSystemResource(file));

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            ResponseEntity<String> response = null;

            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(datosFacturacionCeag.getUrlSw(), HttpMethod.POST, requestEntity, String.class);
            System.out.println("Result - status (" + response.getStatusCode() + ") has body: " + response.hasBody());

            int x = response.toString().indexOf("{");
            int x2 = response.toString().indexOf("{", x + 1);
            int y = response.toString().indexOf("}");

            String jsonFormat = response.toString().substring(x2, y + 1);

            JSONObject responseJson = new JSONObject(jsonFormat);
            Map<Object, Object> jsonXml = new HashMap<>();
            jsonXml.put("CadenaOriginal", responseJson.getString("cadenaOriginalSAT"));
            jsonXml.put("Qr", responseJson.getString("qrCode"));
            jsonXml.put("CFDI", responseJson.getString("cfdi"));
            jsonXml.put("UUID", responseJson.getString("uuid"));
            
            guardarXml(jsonXml.get("CFDI").toString(), route + jsonXml.get("UUID").toString() + ".xml");
            // guardar Datos en la tabla
            comprobanteService.addComprobante(jsonXml.get("CFDI").toString(), datosFactura);

            respuestaTDto.setMensaje("Timbrado");
            respuestaTDto.setStatus(0);

            return respuestaTDto;
        } catch (Exception e){ 
            try {
                respuestaTDto.setMensaje(e.getMessage());
                respuestaTDto.setStatus(1);
                return respuestaTDto;
            } catch (Exception ex) {
                respuestaTDto.setMensaje(ex.getMessage());
                respuestaTDto.setStatus(1);
                return respuestaTDto;
            }
        }
    }

    public RespuestaTimbrado cancelarXml(String uuid){
        RespuestaTimbrado respuestaTDto = new RespuestaTimbrado();
        datosFacturacionCeag = new DatosFacturacionCeag(FacturacionCeagStatus.TIPO_PRODUCCION);
        
        try {
            String token = getToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Optional<ComprobanteEntity> comprobante = comprobanteRepository.findByUuidAndStatus(uuid, true);

            DatosCancelacion datosCancelacion = new DatosCancelacion(
                uuid,
                datosFacturacionCeag.getContraseñaKey(),
                datosFacturacionCeag.getRfc(),
                "02",
                datosFacturacionCeag.getCerb64(),
                datosFacturacionCeag.getKeyb64()
            );
            
            Gson gson = new GsonBuilder().create();
            String jsonBody = gson.toJson(datosCancelacion);
            
            HttpEntity<String> requestEntity = new HttpEntity<String>(jsonBody, headers);
            ResponseEntity<String> response = null;

            try {
                ComprobanteEntity comprobanteEntity = comprobante.get();
                if(comprobante.isPresent()){
                    comprobanteEntity.setIsTimbrado(false);
                    comprobanteRepository.save(comprobanteEntity);
                }
                RestTemplate restTemplate = new RestTemplate();
                response = restTemplate.exchange(datosFacturacionCeag.getUrlSwCancelar(), HttpMethod.POST, requestEntity, String.class);

                comprobante.get().setStatus(false);
                respuestaTDto.setMensaje("Se cancelo el xml");
                respuestaTDto.setStatus(0);
            } catch (Exception e) {
                respuestaTDto.setMensaje("El xml no se cancelo");
                respuestaTDto.setStatus(1);
            }

            return respuestaTDto;

        } catch (Exception e) {
            respuestaTDto.setMensaje(e.getMessage());
            return respuestaTDto;
        }
    }

    public String getToken() throws Exception{
        try {
            String swLogin = datosFacturacionCeag.getUrlSwLogin();
            HttpHeaders headers = new HttpHeaders();
            headers.add("user", datosFacturacionCeag.getUsuarioSw());
            headers.add("password", datosFacturacionCeag.getContraseñaSw());

            HttpEntity<String> requestEntity = new HttpEntity<>(headers);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.exchange(swLogin, HttpMethod.POST, requestEntity, String.class);
            
            int x = response.toString().indexOf("{");
            int x2 = response.toString().indexOf("{", x + 1);
            int y = response.toString().indexOf("}");

            String jsonFormat = response.toString().substring(x2, y + 1);
            // String jsonString = jsonFormat;
            JSONObject responseJson = new JSONObject(jsonFormat);

            return responseJson.getString("token");
        } catch (Exception e) {
            throw new Exception("No se pudo crear el Token" + e.getMessage());
        }
    }

    public void guardarXml(String xmlString, String ruta) throws ParserConfigurationException, SAXException, IOException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xmlString)));
        TransformerFactory tranFactory = TransformerFactory.newInstance();
        Transformer aTransformer = tranFactory.newTransformer();
        Source src = new DOMSource(document);
        Result dest = new StreamResult(new File(ruta));
        aTransformer.transform(src, dest);
    }
}
