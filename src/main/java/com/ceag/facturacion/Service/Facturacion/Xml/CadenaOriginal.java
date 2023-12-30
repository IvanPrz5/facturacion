package com.ceag.facturacion.Service.Facturacion.Xml;

import java.io.InputStream;
import java.io.StringWriter;
import java.security.Signature;
import java.util.Base64;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.ssl.PKCS8Key;
import com.ceag.facturacion.Utils.DatosFacturacion.DatosFacturacionCeag;

import org.springframework.stereotype.Service;

@Service
public class CadenaOriginal {
    public String getCadenaOriginal(String xmlString){
        try(InputStream xslt = getFileFromResourceAsStream("cfdi/xslt/cadenaoriginal_4_0.xslt")){

            StreamSource sourceXslt = new StreamSource(xslt);
            StreamSource sourceXml = new StreamSource(xmlString);

            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer(sourceXslt);
            StringWriter out = new StringWriter();

            transformer.transform(sourceXml, new StreamResult(out));
            String cadenaOriginal = out.toString();

            return cadenaOriginal;
        } catch (Exception e) {
            throw new IllegalArgumentException("Cadena original" + e.getMessage());
        }
    }

    public String getSelloXml(DatosFacturacionCeag datosFacturacion, String xmlString) throws Exception{
        try(InputStream rutaKey = getFileFromResourceAsStream(datosFacturacion.getRutaKey())) {
            PKCS8Key pkcs8 = new PKCS8Key(rutaKey.readAllBytes(), datosFacturacion.getContraseñaKey().toCharArray());
			java.security.PrivateKey pk = pkcs8.getPrivateKey();

			Signature signature = Signature.getInstance("SHA256withRSA");
			signature.initSign(pk);
			signature.update(getCadenaOriginal(xmlString).getBytes("UTF-8"));
			String selloCfdi = new String(Base64.getEncoder().encode(signature.sign()));
            
            return selloCfdi;
        } catch (Exception e) {
            throw new Exception("Sellar xml" + e.getMessage());
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