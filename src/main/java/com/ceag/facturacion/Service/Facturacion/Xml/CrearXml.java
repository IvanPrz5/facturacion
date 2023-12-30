package com.ceag.facturacion.Service.Facturacion.Xml;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import com.ceag.facturacion.Utils.DatosFactura.DatosFactura;
import com.ceag.facturacion.Utils.DatosFacturacion.DatosFacturacionCeag;
import com.ceag.facturacion.Utils.DatosFacturacion.FacturacionCeagStatus;

import org.springframework.stereotype.Service;

@Service
public class CrearXml {
    public String formarXml(DatosFactura datosFactura){
        try {
            System.out.println(datosFactura);
            DatosFacturacionCeag datosFacturacion = new DatosFacturacionCeag(FacturacionCeagStatus.TIPO_PRODUCCION);

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            
            String nameSpace = "http://www.sat.gob.mx/cfd/4";
            String prefijo = "cfdi:";

            DecimalFormat df = new DecimalFormat("0.00");

            String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();

            TimeZone timeZone = TimeZone.getTimeZone("America/Mexico_City");
            SimpleDateFormat formatterWithTimeZone = new SimpleDateFormat(DATE_FORMAT);
            formatterWithTimeZone.setTimeZone(timeZone);
            String sDate = formatterWithTimeZone.format(date);
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
            Date dateWithTimeZone = formatter.parse(sDate);
            formatter.format(dateWithTimeZone);

            Element comprobante = document.createElementNS(nameSpace, prefijo + "Comprobante");
            document.appendChild(comprobante);
            comprobante.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
            comprobante.setAttribute("xmlns:implocal", "http://www.sat.gob.mx/implocal");
            comprobante.setAttribute("xsi:schemaLocation","http://www.sat.gob.mx/cfd/4 http://www.sat.gob.mx/sitio_internet/cfd/4/cfdv40.xsd http://www.sat.gob.mx/implocal http://www.sat.gob.mx/sitio_internet/cfd/implocal/implocal.xsd");
            comprobante.setAttribute("Version", "4.0");

            comprobante.setAttribute("TipoDeComprobante", datosFactura.getDatosComprobante().getIdTipoComprobante());
            comprobante.setAttribute("Exportacion", datosFactura.getDatosComprobante().getIdExportacion());
            comprobante.setAttribute("MetodoPago", datosFactura.getDatosComprobante().getIdMetodoPago());
            comprobante.setAttribute("FormaPago", datosFactura.getDatosComprobante().getIdFormaPago());
            comprobante.setAttribute("LugarExpedicion", "70420");

            comprobante.setAttribute("Fecha",  formatter.format(dateWithTimeZone).replace(" ", "T"));
            comprobante.setAttribute("NoCertificado", datosFacturacion.getNoCertificado());
            comprobante.setAttribute("Certificado", datosFacturacion.getCerb64());
            comprobante.setAttribute("Sello", "");
            comprobante.setAttribute("Moneda", "MXN");

            Element emisor = document.createElement(prefijo + "Emisor");
            comprobante.appendChild(emisor);

            emisor.setAttribute("Nombre", datosFacturacion.getNombreEmisor());
            emisor.setAttribute("Rfc", datosFacturacion.getRfc());
            emisor.setAttribute("RegimenFiscal", datosFacturacion.getRegimenFiscal());
            System.out.println(datosFacturacion.getNombreEmisor());

            Element receptor = document.createElement(prefijo + "Receptor");
            comprobante.appendChild(receptor);

            receptor.setAttribute("Nombre", datosFactura.getDatosReceptor().getNombre());
            receptor.setAttribute("Rfc", datosFactura.getDatosReceptor().getRfc());
            receptor.setAttribute("DomicilioFiscalReceptor", datosFactura.getDatosReceptor().getIdCodigoPostal().toString());
            receptor.setAttribute("RegimenFiscalReceptor", datosFactura.getDatosReceptor().getCodRegimenFiscal().toString());
            receptor.setAttribute("UsoCFDI", datosFactura.getDatosReceptor().getCodUsoCfdi().toString());

            Element conceptos = document.createElement(prefijo + "Conceptos");
            comprobante.appendChild(conceptos);
            
            Double limit = 5.00;

            // String descuentos = "0.00";

            for(int i=0; i<datosFactura.getDatosConcepto().size(); i++){
                Element concepto = document.createElement(prefijo + "Concepto");
                conceptos.appendChild(concepto);
                concepto.setAttribute("ClaveProdServ", datosFactura.getDatosConcepto().get(i).getIdClaveProdServ());
                concepto.setAttribute("Cantidad", datosFactura.getDatosConcepto().get(i).getCantidad());
                concepto.setAttribute("ClaveUnidad", datosFactura.getDatosConcepto().get(i).getIdClaveUnidad());
                concepto.setAttribute("Unidad", datosFactura.getDatosConcepto().get(i).getUnidad());
                concepto.setAttribute("Descripcion", datosFactura.getDatosConcepto().get(i).getDescripcion());
                concepto.setAttribute("ValorUnitario",
                df.format(datosFactura.getDatosConcepto().get(i).getValorUnitario()).replace(",", "."));
            }

            String xmlString = DocumentAsString(document);
            CadenaOriginal cadenaOriginal = new CadenaOriginal();
            // String sello = cadenaOriginal.getSelloXml(datosFacturacion, xmlString);
            // StringUtils.replaceOnce(xmlString, "Sello=\"\" ", " Sello=\"" + sello + "\" ");
            /* String subTotales = df.format(Double.parseDouble(datosFactura.getSubTotal().toString()));
            comprobante.setAttribute("SubTotal", subTotales.replace(",", ".")); 
            comprobante.setAttribute("Folio", datosFactura.getFolio().toString());
            */

            /* String descuentos = "0.00";
            if (datosFactura.getDescuento().toString().contains("-")) {
                descuentos = datosFactura.getDescuento().toString().replace("-", "");
                descuentos = df.format(Double.parseDouble(descuentos)).replace(",", ".");
            } else {
                descuentos = datosFactura.getDescuento().toString();
                descuentos = df.format(Double.parseDouble(descuentos)).replace(",", ".");
            }

            comprobante.setAttribute("Descuento", descuentos);
            String totales = df.format(Double.parseDouble(complementosDto.getTotal().toString()));
            comprobante.setAttribute("Total", totales.replace(",", "."));
             */

            guardarXml(xmlString);
            
            return xmlString;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public String DocumentAsString(Document doc){
        try {
            StringWriter sw = new StringWriter();
            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            transformer.transform(new DOMSource(doc), new StreamResult(sw));
            return sw.toString();
        } catch (Exception ex) {
            throw new RuntimeException("Error converting to String", ex);
        }
    }

    public void guardarXml(String xmlString){
        try {
            String directorio = System.getProperty("user.dir") + "/src/main/java/com/ceag/facturacion/Archivos/";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlString)));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(directorio + "prueba.xml"));
            transformer.transform(source, result);
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }

    /* public List<DatosConcepto> listaConceptos(){
        
        for(int i=0; ){

        }
    } */
}
