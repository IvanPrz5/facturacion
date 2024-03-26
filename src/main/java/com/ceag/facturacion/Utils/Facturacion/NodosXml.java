package com.ceag.facturacion.Utils.Facturacion;

import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;
import java.util.stream.Collectors;

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

import com.ceag.facturacion.Entity.Empresas.EmpresasEntity;
import com.ceag.facturacion.Utils.DatosFactura.DatosFactura;
import com.ceag.facturacion.Utils.DatosFactura.DatosImpuesto;
import com.ceag.facturacion.Utils.DatosFactura.DatosLocales;
import com.ceag.facturacion.Utils.DatosFacturacion.DatosFacturacionCeag;
import com.ceag.facturacion.Utils.DatosFacturacion.FacturacionCeagStatus;

public class NodosXml {
        Element traslados = null;
        Element retenciones = null;

        public String xmlComprobanteAndNodos(DatosFactura datosFactura, Optional<EmpresasEntity> empresas)
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

                        comprobante.setAttribute("TipoDeComprobante",
                                        datosFactura.getDatosComprobante().getIdTipoComprobante());
                        comprobante.setAttribute("Exportacion", datosFactura.getDatosComprobante().getIdExportacion());
                        comprobante.setAttribute("MetodoPago", datosFactura.getDatosComprobante().getIdMetodoPago());
                        comprobante.setAttribute("FormaPago", datosFactura.getDatosComprobante().getIdFormaPago());
                        comprobante.setAttribute("LugarExpedicion",
                                        datosFactura.getDatosComprobante().getLugarExpedicion());

                        comprobante.setAttribute("Fecha", formatter.format(dateWithTimeZone).replace(" ", "T"));
                        comprobante.setAttribute("NoCertificado", empresas.get().getNumCertificado());

                        comprobante.setAttribute("Certificado", empresas.get().getCerB64());

                        // Sello se edita en la clase Facturacion Service
                        comprobante.setAttribute("Sello", "");
                        comprobante.setAttribute("Moneda", "MXN");

                        if (datosFactura.getDatosComprobante().getFolio() != null) {
                                comprobante.setAttribute("Folio", datosFactura.getDatosComprobante().getFolio());
                        }

                        if (datosFactura.getDatosComprobante().getSerie() != null) {
                                comprobante.setAttribute("Serie", datosFactura.getDatosComprobante().getSerie());
                        }

                        String xmlString = DocumentAsString(
                                        xmlNodoEmisorAndReceptor(datosFactura, document, comprobante, prefijo,
                                                        datosFacturacion, empresas));

                        return xmlString;
                } catch (Exception e) {
                        throw new IllegalArgumentException(e.getMessage());
                }
        }

        public Document xmlNodoEmisorAndReceptor(DatosFactura datosFactura, Document document, Element comprobante,
                        String prefijo, DatosFacturacionCeag datosFacturacion, Optional<EmpresasEntity> empresas) {

                Element emisor = document.createElement(prefijo + "Emisor");
                comprobante.appendChild(emisor);

                emisor.setAttribute("Nombre", empresas.get().getNombre());
                emisor.setAttribute("Rfc", empresas.get().getRfc());
                emisor.setAttribute("RegimenFiscal", empresas.get().getIdRegimenFiscal().getCodigo());

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
                        concepto.setAttribute("ClaveProdServ",
                                        datosFactura.getDatosConcepto().get(i).getIdClaveProdServ());
                        concepto.setAttribute("Cantidad",
                                        df.format(Double.parseDouble(
                                                        datosFactura.getDatosConcepto().get(i).getCantidad()))
                                                        .replace(",", "."));
                        concepto.setAttribute("ClaveUnidad", datosFactura.getDatosConcepto().get(i).getIdClaveUnidad());
                        concepto.setAttribute("Unidad", datosFactura.getDatosConcepto().get(i).getUnidad());
                        concepto.setAttribute("Descripcion", datosFactura.getDatosConcepto().get(i).getDescripcion());
                        concepto.setAttribute("ObjetoImp", datosFactura.getDatosConcepto().get(i).getIdObjetoImp());
                        concepto.setAttribute("ValorUnitario",
                                        df.format(datosFactura.getDatosConcepto().get(i).getValorUnitario())
                                                        .replace(",", "."));
                        concepto.setAttribute("Importe",
                                        df.format(datosFactura.getDatosConcepto().get(i).getImporte()).replace(",",
                                                        "."));

                        concepto.setAttribute("Descuento",
                                        df.format(datosFactura.getDatosConcepto().get(i).getDescuento()).replace(",",
                                                        "."));

                        subtotal += datosFactura.getDatosConcepto().get(i).getImporte();
                        descuento += datosFactura.getDatosConcepto().get(i).getDescuento();

                        if (!datosFactura.getDatosConcepto().get(i).getIdObjetoImp().equals("01")) {
                                Element impuestos = document.createElement(prefijo + "Impuestos");
                                concepto.appendChild(impuestos);

                                Map<Boolean, Long> totalTrasladados = datosFactura.getDatosConcepto().get(i)
                                                .getDatosImpuesto().stream()
                                                .filter(t -> t.getIsTrasladado())
                                                .collect(Collectors.groupingBy(DatosImpuesto::getIsTrasladado,
                                                                Collectors.counting()));

                                totalTrasladados.forEach((a, b) -> {
                                        if (b > 0) {
                                                traslados = document.createElement(prefijo + "Traslados");
                                                impuestos.appendChild(traslados);
                                        }
                                });

                                Map<Boolean, Long> totalRetenidos = datosFactura.getDatosConcepto().get(i)
                                                .getDatosImpuesto().stream()
                                                .filter(t -> !t.getIsTrasladado())
                                                .collect(Collectors.groupingBy(DatosImpuesto::getIsTrasladado,
                                                                Collectors.counting()));

                                totalRetenidos.forEach((a, b) -> {
                                        if (b > 0) {
                                                retenciones = document.createElement(prefijo + "Retenciones");
                                                impuestos.appendChild(retenciones);
                                        }
                                });

                                for (int j = 0; j < datosFactura.getDatosConcepto().get(i).getDatosImpuesto()
                                                .size(); j++) {
                                        if (datosFactura.getDatosConcepto().get(i).getDatosImpuesto().get(j)
                                                        .getIsTrasladado()) {
                                                Element traslado = document.createElement(prefijo + "Traslado");
                                                traslados.appendChild(traslado);

                                                traslado.setAttribute("Base",
                                                                df.format(datosFactura.getDatosConcepto().get(i)
                                                                                .getDatosImpuesto().get(j).getBase())
                                                                                .replace(",", "."));
                                                traslado.setAttribute("Impuesto",
                                                                datosFactura.getDatosConcepto().get(i)
                                                                                .getDatosImpuesto().get(j)
                                                                                .getImpuesto());
                                                traslado.setAttribute("TipoFactor",
                                                                datosFactura.getDatosConcepto().get(i)
                                                                                .getDatosImpuesto().get(j)
                                                                                .getTipoFactor());
                                                traslado.setAttribute("TasaOCuota",
                                                                datosFactura.getDatosConcepto().get(i)
                                                                                .getDatosImpuesto().get(j)
                                                                                .getTasaCuota().replace(",", "."));
                                                traslado.setAttribute("Importe",
                                                                df.format(datosFactura.getDatosConcepto().get(i)
                                                                                .getDatosImpuesto()
                                                                                .get(j).getImporte())
                                                                                .replace(",", "."));

                                                baseTraslados += datosFactura.getDatosConcepto().get(i)
                                                                .getDatosImpuesto().get(j).getBase();
                                                impuestosTrasladados += datosFactura.getDatosConcepto().get(i)
                                                                .getDatosImpuesto().get(j)
                                                                .getImporte();
                                        } else {
                                                Element retencion = document.createElement(prefijo + "Retencion");
                                                retenciones.appendChild(retencion);

                                                retencion.setAttribute("Base",
                                                                df.format(datosFactura.getDatosConcepto().get(i)
                                                                                .getDatosImpuesto().get(j).getBase())
                                                                                .replace(",", "."));
                                                retencion.setAttribute("Impuesto",
                                                                datosFactura.getDatosConcepto().get(i)
                                                                                .getDatosImpuesto().get(j)
                                                                                .getImpuesto());
                                                retencion.setAttribute("TipoFactor",
                                                                datosFactura.getDatosConcepto().get(i)
                                                                                .getDatosImpuesto().get(j)
                                                                                .getTipoFactor());
                                                retencion.setAttribute("TasaOCuota",
                                                                datosFactura.getDatosConcepto().get(i)
                                                                                .getDatosImpuesto().get(j)
                                                                                .getTasaCuota().replace(",", "."));
                                                retencion.setAttribute("Importe",
                                                                df.format(datosFactura.getDatosConcepto().get(i)
                                                                                .getDatosImpuesto()
                                                                                .get(j).getImporte())
                                                                                .replace(",", "."));

                                                baseRetenciones += datosFactura.getDatosConcepto().get(i)
                                                                .getDatosImpuesto().get(j).getBase();
                                                impuestosRetenidos += datosFactura.getDatosConcepto().get(i)
                                                                .getDatosImpuesto().get(j).getImporte();
                                        }
                                }
                        }
                }

                Double total = subtotal - descuento;
                comprobante.setAttribute("SubTotal", df.format(subtotal).replace(",", "."));
                comprobante.setAttribute("Descuento", df.format(descuento).replace(",", "."));

                if (datosFactura.getDatosComprobante().getIdTipoComprobante().equals("T")
                                || datosFactura.getDatosComprobante().getIdTipoComprobante().equals("P")) {
                        comprobante.setAttribute("Total", df.format(total).replace(",", "."));
                        return document;
                } else {
                        Double aux = impuestosTrasladados + (-impuestosRetenidos);
                        Double totalAux = total + aux;
                        comprobante.setAttribute("Total", df.format(total + aux).replace(",", "."));
                        return xmlNodoImpuestos(datosFactura, document, comprobante, prefijo, datosFacturacion,
                                        subtotal, descuento,
                                        totalAux, impuestosTrasladados, impuestosRetenidos, baseTraslados,
                                        baseRetenciones);
                }
        }

        public Document xmlNodoImpuestos(DatosFactura datosFactura, Document document, Element comprobante,
                        String prefijo, DatosFacturacionCeag datosFacturacion, Double subtotal, Double descuento,
                        Double total,
                        Double impuestosTrasladados, Double impuestosRetenidos, Double baseTraslados,
                        Double baseRetenciones) {

                Element impuestos = document.createElement(prefijo + "Impuestos");
                comprobante.appendChild(impuestos);
                DecimalFormat df = new DecimalFormat("0.00");

                List<DatosImpuesto> lista = new ArrayList<>();
                for (int i = 0; i < datosFactura.getDatosConcepto().size(); i++) {
                        for (int j = 0; j < datosFactura.getDatosConcepto().get(i).getDatosImpuesto().size(); j++) {
                                lista.add(datosFactura.getDatosConcepto().get(i).getDatosImpuesto().get(j));

                        }
                }

                if (impuestosRetenidos > 0.00) {
                        impuestos.setAttribute("TotalImpuestosRetenidos",
                                        df.format(impuestosRetenidos).replace(",", "."));
                        Element retenciones = document.createElement(prefijo + "Retenciones");
                        impuestos.appendChild(retenciones);

                        Map<String, Map<String, Map<String, List<DatosImpuesto>>>> listRetenidos = lista.stream()
                                        .filter(t -> !t.getIsTrasladado())
                                        .collect(Collectors.groupingBy(DatosImpuesto::getImpuesto,
                                                        Collectors.groupingBy(DatosImpuesto::getTasaCuota,
                                                                        Collectors.groupingBy(
                                                                                        DatosImpuesto::getTipoFactor))));

                        listRetenidos.forEach((a, b) -> {
                                b.forEach((c, d) -> {
                                        d.forEach((e, f) -> {
                                                Double imp = f.stream().mapToDouble(DatosImpuesto::getImporte).sum();
                                                Element retencion = document.createElement(prefijo + "Retencion");
                                                retenciones.appendChild(retencion);

                                                // retencion.setAttribute("Base", df.format(baseRetenciones));
                                                retencion.setAttribute("Impuesto", a);
                                                // retencion.setAttribute("TipoFactor", "Tasa");
                                                // retencion.setAttribute("TasaOCuota", "0.160000");
                                                retencion.setAttribute("Importe", df.format(imp).replace(",", "."));
                                        });
                                });
                        });

                }

                if (impuestosTrasladados > 0.00) {
                        impuestos.setAttribute("TotalImpuestosTrasladados",
                                        df.format(impuestosTrasladados).replace(",", "."));

                        Element traslados = document.createElement(prefijo + "Traslados");
                        impuestos.appendChild(traslados);

                        Map<String, Map<String, Map<String, List<DatosImpuesto>>>> listTrasladados = lista.stream()
                                        .filter(DatosImpuesto::getIsTrasladado)
                                        .collect(Collectors.groupingBy(DatosImpuesto::getImpuesto,
                                                        Collectors.groupingBy(DatosImpuesto::getTasaCuota,
                                                                        Collectors.groupingBy(
                                                                                        DatosImpuesto::getTipoFactor))));

                        listTrasladados.forEach((a, b) -> {
                                b.forEach((c, d) -> {
                                        d.forEach((e, f) -> {
                                                Double imp = f.stream().mapToDouble(DatosImpuesto::getImporte).sum();
                                                Double base = f.stream().mapToDouble(DatosImpuesto::getBase).sum();
                                                Element traslado = document.createElement(prefijo + "Traslado");
                                                traslados.appendChild(traslado);

                                                traslado.setAttribute("Base", df.format(base).replace(",", "."));
                                                traslado.setAttribute("Impuesto", a);
                                                traslado.setAttribute("TasaOCuota", c.replace(",", "."));
                                                traslado.setAttribute("TipoFactor", e);
                                                traslado.setAttribute("Importe", df.format(imp).replace(",", "."));
                                        });
                                });
                        });
                }

                if (datosFactura.getDatosLocales().size() > 0) {
                        Double totalRetenciones = 0.00;
                        Double totalTrasladados = 0.00;

                        for (int i = 0; i < datosFactura.getDatosLocales().size(); i++) {
                                if (datosFactura.getDatosLocales().get(i).getIsTrasladado()) {
                                        totalTrasladados += datosFactura.getDatosLocales().get(i).getImporte();
                                } else {
                                        totalRetenciones += datosFactura.getDatosLocales().get(i).getImporte();
                                }
                        }
                        Double aux = totalTrasladados + (-totalRetenciones);
                        comprobante.setAttribute("Total", df.format(total + aux).replace(",", "."));

                        return xmlNodoComplemento(datosFactura, document, comprobante, prefijo, datosFacturacion,
                                        totalTrasladados, totalRetenciones);
                } else {
                        return document;
                }
        }

        public Document xmlNodoComplemento(DatosFactura datosFactura, Document document, Element comprobante,
                        String prefijo, DatosFacturacionCeag datosFacturacion, Double totalTrasladados,
                        Double totalRetenidos) {

                Element complemento = document.createElement(prefijo + "Complemento");
                comprobante.appendChild(complemento);
                DecimalFormat df = new DecimalFormat("0.00");

                String prefCom = "implocal:";
                List<DatosLocales> lista = new ArrayList<>();
                Element traslados = document.createElement(prefCom + "ImpuestosLocales");
                complemento.appendChild(traslados);
                traslados.setAttribute("version", "1.0");

                traslados.setAttribute("TotaldeRetenciones", df.format(totalRetenidos).replace(",", "."));
                traslados.setAttribute("TotaldeTraslados", df.format(totalTrasladados).replace(",", "."));

                for (int i = 0; i < datosFactura.getDatosLocales().size(); i++) {
                        lista.add(datosFactura.getDatosLocales().get(i));
                }

                if(totalRetenidos > 0){
                        Map<String, Map<String, List<DatosLocales>>> listRetenidos = lista.stream()
                                        .filter(t -> !t.getIsTrasladado())
                                        .collect(Collectors.groupingBy(DatosLocales::getImpuesto,
                                                        Collectors.groupingBy(DatosLocales::getTasaCuota)));

                        listRetenidos.forEach((a, b) -> {
                                b.forEach((c, d) -> {
                                        Double imp = d.stream().mapToDouble(DatosLocales::getImporte).sum();
                                        Element retenido = document.createElement(prefCom + "RetencionesLocales");
                                        traslados.appendChild(retenido);
                                        retenido.setAttribute("ImpLocRetenido", a);
                                        retenido.setAttribute("TasadeRetencion", c.replace(",", "."));
                                        retenido.setAttribute("Importe", df.format(imp).replace(",", "."));
                                });
                        });
                }

                if(totalTrasladados > 0){
                        Map<String, Map<String, List<DatosLocales>>> listTrasladados = lista.stream()
                                .filter(DatosLocales::getIsTrasladado)
                                .collect(Collectors.groupingBy(DatosLocales::getImpuesto,
                                                Collectors.groupingBy(DatosLocales::getTasaCuota)));

                        listTrasladados.forEach((a, b) -> {
                                b.forEach((c, d) -> {
                                        Double imp = d.stream().mapToDouble(DatosLocales::getImporte).sum();
                                        Element traslado = document.createElement(prefCom + "TrasladosLocales");
                                        traslados.appendChild(traslado);

                                        traslado.setAttribute("ImpLocTrasladado", a);
                                        traslado.setAttribute("TasadeTraslado", c.replace(",", "."));
                                        traslado.setAttribute("Importe", df.format(imp).replace(",", "."));
                                });
                        });
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
