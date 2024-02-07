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

import com.ceag.facturacion.Dto.Facturacion.RespuestaTimbrado;
import com.ceag.facturacion.Entity.Empresas.EmpresasEntity;
import com.ceag.facturacion.Entity.Facturacion.ComprobanteEntity;
import com.ceag.facturacion.Repository.Facturacion.ComprobanteRepository;
import com.ceag.facturacion.Utils.DatosFactura.DatosCancelacion;
import com.ceag.facturacion.Utils.DatosFactura.DatosFactura;
import com.ceag.facturacion.Utils.DatosFactura.DatosXml;
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

    public RespuestaTimbrado timbrarXml(String route, DatosFactura datosFactura, Optional<EmpresasEntity> empresas){
        RespuestaTimbrado respuestaTDto = new RespuestaTimbrado();
        datosFacturacionCeag = new DatosFacturacionCeag(FacturacionCeagStatus.TIPO_PRODUCCION);
        
        try {
            String token = getToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            DatosXml datosXml = new DatosXml(route);
            Gson gson = new GsonBuilder().create();
            String jsonBody = gson.toJson(datosXml);
        
            HttpEntity<String> requestEntity = new HttpEntity<String>(jsonBody, headers);
            ResponseEntity<String> response = null;

            RestTemplate restTemplate = new RestTemplate();
            response = restTemplate.exchange(datosFacturacionCeag.getUrlSw(), HttpMethod.POST, requestEntity, String.class);

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
            
            String directorio = System.getProperty("user.dir") + "/src/main/java/com/ceag/facturacion/Archivos/";
            // guardar Datos en la tabla
            guardarXml(jsonXml.get("CFDI").toString(), directorio + jsonXml.get("UUID").toString() + ".xml");
            comprobanteService.addComprobante(jsonXml.get("CFDI").toString(), datosFactura, empresas);
            
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

    public RespuestaTimbrado cancelarXml(ComprobanteEntity comprobanteJson){
        RespuestaTimbrado respuestaTDto = new RespuestaTimbrado();
        datosFacturacionCeag = new DatosFacturacionCeag(FacturacionCeagStatus.TIPO_PRODUCCION);
        
        try {
            String token = getToken();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token);
            headers.setContentType(MediaType.APPLICATION_JSON);

            DatosCancelacion datosCancelacion = new DatosCancelacion(
                comprobanteJson.getUuid(),
                comprobanteJson.getIdEmpresa().getPassKey(),
                comprobanteJson.getIdEmpresa().getRfc(),
                "02",
                comprobanteJson.getIdEmpresa().getCerB64(),
                comprobanteJson.getIdEmpresa().getKeyB64()
            );
            
            Gson gson = new GsonBuilder().create();
            String jsonBody = gson.toJson(datosCancelacion);
            
            HttpEntity<String> requestEntity = new HttpEntity<String>(jsonBody, headers);
            ResponseEntity<String> response = null;

            Optional<ComprobanteEntity> comprobante = comprobanteRepository.findByUuidAndStatus(comprobanteJson.getUuid(), true);
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
