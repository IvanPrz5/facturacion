package com.ceag.facturacion.Service.Facturacion;

import java.io.File;
import java.io.StringReader;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Xml.BuscarUuidDto;
import com.ceag.facturacion.Dto.Xml.RespuestaTimbrado;
import com.ceag.facturacion.Entity.Facturacion.ComprobanteEntity;
import com.ceag.facturacion.Repository.Facturacion.ComprobanteRepository;
import com.ceag.facturacion.Utils.DatosFactura.DatosFactura;
import com.ceag.facturacion.Utils.DatosFacturacion.DatosFacturacionCeag;
import com.ceag.facturacion.Utils.DatosFacturacion.FacturacionCeagStatus;
import com.ceag.facturacion.Utils.Facturacion.CadenaOriginal;
import com.ceag.facturacion.Utils.Facturacion.NodosXml;

@Service
public class CrearXmlService {

    @Autowired
    ComprobanteRepository comprobanteRepository;

    @Autowired
    SwXmlService swXmlService;

    @Autowired
    ComprobanteService comprobanteService;

    public RespuestaTimbrado formarAndTimbrarXml(DatosFactura datosFactura) throws Exception {
        try {
            DatosFacturacionCeag datosFacturacion = new DatosFacturacionCeag(FacturacionCeagStatus.TIPO_PRODUCCION);

            RespuestaTimbrado respuestaTDto = new RespuestaTimbrado();

            NodosXml nodosXml = new NodosXml();
            String xmlString = nodosXml.xmlComprobanteAndNodos(datosFactura);

            CadenaOriginal cadenaOriginal = new CadenaOriginal();
            String sello = cadenaOriginal.getSelloXml(datosFacturacion, xmlString);

            xmlString = StringUtils.replaceOnce(xmlString, "Sello=\"\" ", " Sello=\"" + sello + "\" ");
            String exit = guardarXml(xmlString);

            // BuscarUuidDto isTimbrado = isTimbradoByUuid()
            if(exit.equals("1")){
                String directorio = System.getProperty("user.dir") + "/src/main/java/com/ceag/facturacion/Archivos/";
                if(!datosFactura.getDatosComprobante().getIsTimbrado()){
                    // guardamos
                    comprobanteService.addComprobante(xmlString, datosFactura);
                    respuestaTDto.setMensaje("Los datos se guardaron para timbrar despues");
                    respuestaTDto.setStatus(0);
                }else{
                    //guardamos y timbramos guardar en metodo por UUID
                    respuestaTDto = swXmlService.timbrarXml(directorio, datosFactura);
                }
            }else{
                respuestaTDto.setMensaje("El folio ya tiene un timbrado");
                respuestaTDto.setStatus(1);
            }

            return respuestaTDto;
        } catch (Exception e) {
            throw new Exception("Error al formar xml " + e.getMessage());
        }
    }

    
    public String guardarXml(String xmlString) throws Exception{
        try {
            String directorio = System.getProperty("user.dir") +
            "/src/main/java/com/ceag/facturacion/Archivos/";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlString)));
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(directorio + "guardado.xml"));
            
            transformer.transform(source, result);
        
            return "1";
        } catch (TransformerException e) {
            throw new RuntimeException("No se guardo el Xml " + e.getMessage());
        }
    }

    public BuscarUuidDto isTimbradoByUuid(String uuid) throws Exception{
        BuscarUuidDto uuidDto = new BuscarUuidDto();
        try {
            Optional<ComprobanteEntity> comprobante = comprobanteRepository.findByUuidAndStatus(uuid, true);
            if(comprobante.isPresent()){
                uuidDto.setStatus(true);
                uuidDto.setUuid(comprobante.get().getUuid());
                return uuidDto;
            }else{
                uuidDto.setStatus(false);
                uuidDto.setUuid("");
                return uuidDto;
            }
        } catch (Exception e) {
            throw new Exception("No se encontrol el uuid" + e.getMessage());
        }
    }
}
