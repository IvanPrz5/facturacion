package com.ceag.facturacion.Utils.Facturacion;

import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class JasperPdf {
    public ResponseEntity <Resource> crearPdf() throws Exception {
        String directorio = System.getProperty("user.dir")
                + "/src/main/java/com/ceag/facturacion/Archivos";
        try{
            InputStream xml = getFileFromResourceAsStream("5cf8e946-5db8-41f2-ac18-9a5b55f4a26d.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(xml);
            document.getDocumentElement().normalize();

            Map<String, Object> parameters = new HashMap<>();
            InputStream isJasper = getFileFromResourceAsStream("comprobanteFiscal.jasper");
            JasperReport jasper = (JasperReport) JRLoader.loadObject(isJasper);

            // Logo es de la empresa que se factura
            // File logo = new File(rutaLogo);

            // Ruta del qr si existe
            // File imageQr = new File(rutaQr);
            // parameters.put("logo", );
            
            

            return null;
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    public String toFormatMxn(String cantidad){
        try {
            Locale locale = new Locale("es", "MX");
            NumberFormat nf = NumberFormat.getInstance(locale);
            DecimalFormat df = (DecimalFormat) nf;
            df.applyPattern("###,###,##0.00");
            return nf.format(Double.parseDouble(cantidad));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("file not found! " + fileName);
        } else {
            return inputStream;
        }
    }
}
