package com.ceag.facturacion.Service.Facturacion;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Entity.Facturacion.ComprobanteEntity;
import com.ceag.facturacion.Entity.Facturacion.XmlEntity;
import com.ceag.facturacion.Repository.Facturacion.XmlRepository;

@Service
public class XmlService {

    @Autowired
    XmlRepository xmlRepository;

    public ResponseEntity<XmlEntity> addRegister(Map<Object, Object> jsonXml, ComprobanteEntity comprobanteEntity){
        try {
            XmlEntity xmlEntity = new XmlEntity();
            xmlEntity.setXmlString(jsonXml.get("CFDI").toString());
            xmlEntity.setCvv(jsonXml.get("Qr").toString());
            xmlEntity.setUuid(jsonXml.get("UUID").toString());
            xmlEntity.setCadenaOriginal(jsonXml.get("CadenaOriginal").toString());
            xmlEntity.setIdComprobante(comprobanteEntity);
            xmlEntity.setStatus(true);
            xmlRepository.save(xmlEntity);

            return null;
        } catch (Exception e) {
            throw new IllegalArgumentException("No se agrego el xml: " + e.getMessage());
        }
    }

    public ResponseEntity<Resource> descargarXml(String uuid) throws Exception {
        try {
            List<XmlEntity> xmlString = xmlRepository.findByUuid(uuid);

            byte[] reporte = xmlString.get(0).getXmlString().getBytes();

            String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
            StringBuilder stringBuilder = new StringBuilder().append("InvoiceXml:");
            
            ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                    .filename(stringBuilder.append(uuid)
                            .append("generateDate:")
                            .append(sdf)
                            .append(".xml")
                            .toString())
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(contentDisposition);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_XML)
                    .headers(headers).body(new ByteArrayResource(reporte));
        } catch (Exception e) {
            throw new Exception("No se descargo el Xml " + e.getMessage());
        }
    }

    public ResponseEntity<Resource> descargarCvv(String uuid) throws Exception {
        try {
            List<XmlEntity> xmlString = xmlRepository.findByUuid(uuid);
            // byte[] decodeBytes = Base64.getDecoder().decode(base64);
            byte[] reporte = Base64.getDecoder().decode(xmlString.get(0).getCvv());

            String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
            StringBuilder stringBuilder = new StringBuilder().append("InvoicePng:");

            ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                    .filename(stringBuilder.append(uuid)
                            .append("generateDate:")
                            .append(sdf)
                            .append(".png")
                            .toString())
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(contentDisposition);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .headers(headers).body(new ByteArrayResource(reporte));
        } catch (Exception e) {
            throw new Exception("No se descargo el Cvv " + e.getMessage());
        }
    }
}
