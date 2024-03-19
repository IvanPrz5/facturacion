package com.ceag.facturacion.Utils.Facturacion;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.security.Signature;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Optional;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.ssl.PKCS8Key;

import com.ceag.facturacion.Entity.Empresas.EmpresasEntity;
import com.ceag.facturacion.Utils.DatosFacturacion.DatosFacturacionCeag;

public class CadenaOriginal {
    public String getCadenaOriginal(String xmlString){
        try(InputStream xslt = getFileFromResourceAsStream("cfdi/xslt/cadenaoriginal_4_0.xslt")){

            StreamSource sourceXslt = new StreamSource(xslt);
            StreamSource sourceXml = new StreamSource(new StringReader(xmlString));

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

    public String getSelloXml(DatosFacturacionCeag datosFacturacion, Optional<EmpresasEntity> empresa, String xmlString) throws Exception{
        try {
            byte[] bytesKey = Base64.getDecoder().decode(empresa.get().getKeyB64());
            PKCS8Key pkcs8 = new PKCS8Key(bytesKey, empresa.get().getPassKey().toCharArray());
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

    public String getNoCertificado(String cerB64) throws Exception{
        try {
            byte[] toBytes = Base64.getDecoder().decode(cerB64.getBytes());
            InputStream is = new ByteArrayInputStream(toBytes);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate certificado = (X509Certificate) cf.generateCertificate(is);
            byte[] byteArray = certificado.getSerialNumber().toByteArray();
            String noCertificado = new String(byteArray);
            return noCertificado;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
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