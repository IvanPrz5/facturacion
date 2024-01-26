package com.ceag.facturacion.Utils.Facturacion;

import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ceag.facturacion.Utils.DatosFactura.DatosFactura;
import com.ceag.facturacion.Utils.DatosFacturacion.DatosFacturacionCeag;
import com.ceag.facturacion.Utils.DatosFacturacion.FacturacionCeagStatus;

public class NodosXml {
    public String xmlComprobanteAndNodos(DatosFactura datosFactura)
            throws ParseException, ParserConfigurationException {
        DatosFacturacionCeag datosFacturacion = new DatosFacturacionCeag(FacturacionCeagStatus.TIPO_PRODUCCION);

        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            String nameSpace = "http://www.sat.gob.mx/cfd/4";
            String prefijo = "cfdi:";

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
            comprobante.setAttribute("xsi:schemaLocation",
                    "http://www.sat.gob.mx/cfd/4 http://www.sat.gob.mx/sitio_internet/cfd/4/cfdv40.xsd http://www.sat.gob.mx/implocal http://www.sat.gob.mx/sitio_internet/cfd/implocal/implocal.xsd");
            comprobante.setAttribute("Version", "4.0");

            comprobante.setAttribute("TipoDeComprobante", datosFactura.getDatosComprobante().getIdTipoComprobante());
            comprobante.setAttribute("Exportacion", datosFactura.getDatosComprobante().getIdExportacion());
            comprobante.setAttribute("MetodoPago", datosFactura.getDatosComprobante().getIdMetodoPago());
            comprobante.setAttribute("FormaPago", datosFactura.getDatosComprobante().getIdFormaPago());
            comprobante.setAttribute("LugarExpedicion", "70420");

            comprobante.setAttribute("Fecha", formatter.format(dateWithTimeZone).replace(" ", "T"));
            comprobante.setAttribute("NoCertificado", datosFacturacion.getNoCertificado());
            comprobante.setAttribute("Certificado", datosFacturacion.getCerb64());
            comprobante.setAttribute("Sello", "");
            comprobante.setAttribute("Moneda", "MXN");

            // String xmlString = DocumentAsString(document);
            // Document algo = xmlNodoEmisorAndReceptor(datosFactura, document, comprobante,
            // prefijo, datosFacturacion);
            String xmlString = DocumentAsString(
                    xmlNodoEmisorAndReceptor(datosFactura, document, comprobante, prefijo, datosFacturacion));

            return xmlString;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public Document xmlNodoEmisorAndReceptor(DatosFactura datosFactura, Document document, Element comprobante,
            String prefijo, DatosFacturacionCeag datosFacturacion) {

        Element emisor = document.createElement(prefijo + "Emisor");
        comprobante.appendChild(emisor);

        emisor.setAttribute("Nombre", datosFacturacion.getNombreEmisor());
        emisor.setAttribute("Rfc", datosFacturacion.getRfc());
        emisor.setAttribute("RegimenFiscal", datosFacturacion.getRegimenFiscal());

        Element receptor = document.createElement(prefijo + "Receptor");
        comprobante.appendChild(receptor);

        receptor.setAttribute("Nombre", datosFactura.getDatosReceptor().getNombre());
        receptor.setAttribute("Rfc", datosFactura.getDatosReceptor().getRfc());
        receptor.setAttribute("DomicilioFiscalReceptor",
                datosFactura.getDatosReceptor().getDomicilioFiscal());
        receptor.setAttribute("RegimenFiscalReceptor",
                datosFactura.getDatosReceptor().getRegimenFiscal());
        receptor.setAttribute("UsoCFDI", datosFactura.getDatosReceptor().getUsoCfdi());

        return xmlNodoConceptos(datosFactura, document, comprobante, prefijo, datosFacturacion);
    }

    public Document xmlNodoConceptos(DatosFactura datosFactura, Document document, Element comprobante,
            String prefijo, DatosFacturacionCeag datosFacturacion) {
        Element conceptos = document.createElement(prefijo + "Conceptos");
        comprobante.appendChild(conceptos);

        // Double limit = 5.00;
        Double descuento = 0.00;
        Double subtotal = 0.00;
        Double baseTraslados = 0.00;
        Double baseRetenciones = 0.00;
        Double impuestosTrasladados = 0.00;
        Double impuestosRetenidos = 0.00;
        DecimalFormat df = new DecimalFormat("0.00");

        for (int i = 0; i < datosFactura.getDatosConcepto().size(); i++) {
            Element concepto = document.createElement(prefijo + "Concepto");
            conceptos.appendChild(concepto);
            concepto.setAttribute("ClaveProdServ", datosFactura.getDatosConcepto().get(i).getIdClaveProdServ());
            concepto.setAttribute("Cantidad", datosFactura.getDatosConcepto().get(i).getCantidad());
            concepto.setAttribute("ClaveUnidad", datosFactura.getDatosConcepto().get(i).getIdClaveUnidad());
            concepto.setAttribute("Unidad", datosFactura.getDatosConcepto().get(i).getUnidad());
            concepto.setAttribute("Descripcion", datosFactura.getDatosConcepto().get(i).getDescripcion());
            concepto.setAttribute("ObjetoImp", datosFactura.getDatosConcepto().get(i).getIdObjetoImp());
            concepto.setAttribute("ValorUnitario",
                    df.format(datosFactura.getDatosConcepto().get(i).getValorUnitario()).replace(",", "."));
            concepto.setAttribute("Importe", df.format(datosFactura.getDatosConcepto().get(i).getImporte()));
            concepto.setAttribute("Descuento", df.format(datosFactura.getDatosConcepto().get(i).getDescuento()));

            subtotal += datosFactura.getDatosConcepto().get(i).getImporte();
            descuento += datosFactura.getDatosConcepto().get(i).getDescuento();

            if (!datosFactura.getDatosConcepto().get(i).getIdObjetoImp().equals("01")) {
                Element impuestos = document.createElement(prefijo + "Impuestos");
                concepto.appendChild(impuestos);

                Element traslados = document.createElement(prefijo + "Traslados");
                impuestos.appendChild(traslados);

                Element retenciones = document.createElement(prefijo + "Retenciones");
                impuestos.appendChild(retenciones);

                for (int j = 0; j < datosFactura.getDatosConcepto().get(i).getDatosImpuesto().size(); j++) {
                    if (datosFactura.getDatosConcepto().get(i).getDatosImpuesto().get(j).getIsTrasladado()) {
                        Element traslado = document.createElement(prefijo + "Traslado");
                        traslados.appendChild(traslado);

                        traslado.setAttribute("Base",
                                df.format(datosFactura.getDatosConcepto().get(i).getDatosImpuesto().get(j).getBase()));
                        traslado.setAttribute("Impuesto",
                                datosFactura.getDatosConcepto().get(i).getDatosImpuesto().get(j).getCodImpuesto());
                        traslado.setAttribute("TipoFactor",
                                datosFactura.getDatosConcepto().get(i).getDatosImpuesto().get(j).getCodTipoFactor());
                        traslado.setAttribute("TasaOCuota",
                                datosFactura.getDatosConcepto().get(i).getDatosImpuesto().get(j).getCodTasaCuota());
                        traslado.setAttribute("Importe",
                                df.format(
                                        datosFactura.getDatosConcepto().get(i).getDatosImpuesto().get(j).getImporte()));

                        baseTraslados += datosFactura.getDatosConcepto().get(i).getDatosImpuesto().get(j).getBase();
                        impuestosTrasladados += datosFactura.getDatosConcepto().get(i).getDatosImpuesto().get(j)
                                .getImporte();
                    } else {
                        Element retencion = document.createElement(prefijo + "Retencion");
                        retenciones.appendChild(retencion);

                        retencion.setAttribute("Base",
                                df.format(datosFactura.getDatosConcepto().get(i).getDatosImpuesto().get(j).getBase()));
                        retencion.setAttribute("Impuesto",
                                datosFactura.getDatosConcepto().get(i).getDatosImpuesto().get(j).getCodImpuesto());
                        retencion.setAttribute("TipoFactor",
                                datosFactura.getDatosConcepto().get(i).getDatosImpuesto().get(j).getCodTipoFactor());
                        retencion.setAttribute("TasaOCuota",
                                datosFactura.getDatosConcepto().get(i).getDatosImpuesto().get(j).getCodTasaCuota());
                        retencion.setAttribute("Importe",
                                df.format(
                                        datosFactura.getDatosConcepto().get(i).getDatosImpuesto().get(j).getImporte()));

                        baseRetenciones += datosFactura.getDatosConcepto().get(i).getDatosImpuesto().get(j).getBase();
                        impuestosRetenidos += datosFactura.getDatosConcepto().get(i).getDatosImpuesto().get(j)
                                .getImporte();
                    }
                }
            }
        }

        Double total = subtotal - descuento;
        comprobante.setAttribute("SubTotal", df.format(subtotal));
        comprobante.setAttribute("Descuento", df.format(descuento));

        if (datosFactura.getDatosComprobante().getIdTipoComprobante().equals("T")
                || datosFactura.getDatosComprobante().getIdTipoComprobante().equals("P")
                || impuestosTrasladados == 0.00) {
            comprobante.setAttribute("Total", df.format(total));
            return document;
        } else {
            Double aux = impuestosTrasladados + (-impuestosRetenidos);
            comprobante.setAttribute("Total", df.format(total + aux));
            return xmlNodoImpuestos(datosFactura, document, comprobante, prefijo, datosFacturacion, subtotal, descuento,
                    total, impuestosTrasladados, impuestosRetenidos, baseTraslados, baseRetenciones);
        }
    }

    public Document xmlNodoImpuestos(DatosFactura datosFactura, Document document, Element comprobante,
            String prefijo, DatosFacturacionCeag datosFacturacion, Double subtotal, Double descuento, Double total,
            Double impuestosTrasladados, Double impuestosRetenidos, Double baseTraslados, Double baseRetenciones) {

        Element impuestos = document.createElement(prefijo + "Impuestos");
        comprobante.appendChild(impuestos);
        DecimalFormat df = new DecimalFormat("0.00");

        if (impuestosRetenidos > 0.00) {
            impuestos.setAttribute("TotalImpuestosRetenidos", df.format(impuestosRetenidos));
            // comprobante.setAttribute("Total", df.format(total - impuestosRetenidos));
            Element retenciones = document.createElement(prefijo + "Retenciones");
            impuestos.appendChild(retenciones);
            Element retencion = document.createElement(prefijo + "Retencion");
            retenciones.appendChild(retencion);
            
            // retencion.setAttribute("Base", df.format(base));
            retencion.setAttribute("Impuesto", "002");
            // retencion.setAttribute("TipoFactor", "Tasa");
            // retencion.setAttribute("TasaOCuota", "0.160000");
            retencion.setAttribute("Importe", df.format(impuestosRetenidos));
        }


        if (impuestosTrasladados > 0.00) {

            impuestos.setAttribute("TotalImpuestosTrasladados", df.format(impuestosTrasladados));
            // comprobante.setAttribute("Total", df.format(total + impuestosTrasladados));
            Element traslados = document.createElement(prefijo + "Traslados");
            impuestos.appendChild(traslados);
            Element traslado = document.createElement(prefijo + "Traslado");
            traslados.appendChild(traslado);
            
            traslado.setAttribute("Base", df.format(baseTraslados));
            traslado.setAttribute("Impuesto", "002");
            traslado.setAttribute("TipoFactor", "Tasa");
            traslado.setAttribute("TasaOCuota", "0.160000");
            traslado.setAttribute("Importe", df.format(impuestosTrasladados));
        }

        return document;
    }

    public String DocumentAsString(Document doc) {
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
            throw new RuntimeException("Error converting to String ", ex);
        }
    }
}
