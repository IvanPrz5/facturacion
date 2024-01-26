package com.ceag.facturacion.Service.Facturacion;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.http.MediaType;
import org.springframework.core.io.ByteArrayResource;

@Service
public class DescargarRecursosService {
    public ResponseEntity<Resource> descargarXml(String uuid) {
        try {
            String directorio = System.getProperty("user.dir") + "/src/main/java/com/ceag/facturacion/Archivos/";
            File factura = new File(directorio + uuid + ".xml");
            /* 
            if(!factura.exists()){ // si no exixte el xml lo regenera
                ComplementosPdfEntity uuidB= complementosPdfRepository.findByUuid(uuid);
                guardarxmlFinal(uuidB.getCfdi(), uuid);
            } */

            byte[] reporte = Files.readAllBytes(factura.toPath());

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
            return null;
        }
    }
}
