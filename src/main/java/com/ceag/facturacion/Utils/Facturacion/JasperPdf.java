package com.ceag.facturacion.Utils.Facturacion;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ceag.facturacion.Entity.Empresas.EmpresasEntity;
import com.ceag.facturacion.Entity.Facturacion.XmlEntity;
import com.ceag.facturacion.Repository.Empresas.EmpresasRepository;
import com.ceag.facturacion.Repository.Facturacion.XmlRepository;
import com.ceag.facturacion.Utils.DatosFactura.ConceptoAux;
import com.fasterxml.jackson.databind.JsonSerializable.Base;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

@Service
public class JasperPdf {
    @Autowired
    XmlRepository xmlRepository;

    @Autowired
    EmpresasRepository empresasRepository;

    public ResponseEntity<Resource> crearPdf(String uuid, Long idEmpresa) throws Exception {
        try {
            List<XmlEntity> xmlString = xmlRepository.findByUuid(uuid);

            Optional<EmpresasEntity> empresa = empresasRepository.findById(idEmpresa);

            InputStream xml = new ByteArrayInputStream(xmlString.get(0).getXmlString().getBytes());
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(xml);
            document.getDocumentElement().normalize();

            Map<String, Object> parametros = new HashMap<>();
            // "cfdi/jasper/cadenaoriginal_4_0.xslt"
            InputStream isJasper = getFileFromResourceAsStream("cfdi/jasper/comprobanteFiscal.jasper");
            JasperReport jasper = (JasperReport) JRLoader.loadObject(isJasper);

            NodeList listComprobante = document.getElementsByTagName("cfdi:Comprobante");
            Node nodeComprobante = listComprobante.item(0);
            Element atribsComprobante = (Element) nodeComprobante;

            byte[] logo = Base64.getDecoder().decode(empresa.get().getLogo().getBytes());
            parametros.put("image", new ByteArrayInputStream(logo));
            byte[] qr = Base64.getDecoder().decode(xmlString.get(0).getCvv().getBytes());
            parametros.put("qr", new ByteArrayInputStream(qr));

            parametros.put("metodoPago", atribsComprobante.getAttribute("MetodoPago"));
            parametros.put("moneda", "MXN");
            parametros.put("codPago", atribsComprobante.getAttribute("FormaPago"));
            parametros.put("formaPago", "Desc cod Pago"); // Revisar

            parametros.put("subTotalSuma", toFormatMxn(atribsComprobante.getAttribute("SubTotal")));
            parametros.put("descuentoSuma", toFormatMxn(atribsComprobante.getAttribute("Descuento")));
            parametros.put("totalSuma", toFormatMxn(atribsComprobante.getAttribute("Total")));

            parametros.put("folioFacturar", "RrevisarFGolioo");

            parametros.put("cuenta", "Revisar Cuenta");
            // parametros.put("caja", "Revisar Caja");

            parametros.put("fechaEmision", atribsComprobante.getAttribute("Fecha"));

            NodeList listEmisor = document.getElementsByTagName("cfdi:Emisor");
            Node nodeEmisor = listEmisor.item(0);
            Element atribsEmisor = (Element) nodeEmisor;

            parametros.put("nombreEmpresa", atribsEmisor.getAttribute("Nombre"));
            parametros.put("rfcEmpresa", atribsEmisor.getAttribute("Rfc"));
            parametros.put("regimenFiscalEmpresa", empresa.get().getIdRegimenFiscal().getCodigo() + " - "
                    + empresa.get().getIdRegimenFiscal().getDescripcion());

            // Receptor
            NodeList listReceptor = document.getElementsByTagName("cfdi:Receptor");
            Node nodeReceptor = listReceptor.item(0);
            Element atribsReceptor = (Element) nodeReceptor;

            parametros.put("nombre", atribsReceptor.getAttribute("Nombre"));
            parametros.put("rfc", atribsReceptor.getAttribute("Rfc"));
            parametros.put("codRegimenFiscal", atribsReceptor.getAttribute("RegimenFiscalReceptor"));
            parametros.put("regimenFiscal", "Personas Morales Con Fines No Lucrativos");
            parametros.put("uso", atribsReceptor.getAttribute("UsoCFDI"));
            parametros.put("usoLetra", "Uso Cfdi en letra");
            parametros.put("domicilio", "Revisar Domicilio");

            parametros.put("datosCuenta", "Revisar datosCuenta");
            parametros.put("datosPadron", "Revisar datos PADRON");
            parametros.put("observaciones", "");
            parametros.put("fechaRecibo", "fECHJA rECIBO");

            // Conceptos
            // Conceptos
            NodeList listConcepto = document.getElementsByTagName("cfdi:Concepto");
            List<ConceptoAux> conceptoList = new ArrayList<>();
            List<Double> valorUnitario = new ArrayList<>();
            List<Double> descuento = new ArrayList<>();
            List<String> suma = new ArrayList<>();
            DecimalFormat mf = new DecimalFormat("0.00");
            mf.setMinimumFractionDigits(2);
            ConceptoAux conceptoAux = null;
            for (int i = 0; i < listConcepto.getLength(); i++) {
                Node nodeConcepto = listConcepto.item(i);
                Element atribsConcepto = (Element) nodeConcepto;
                valorUnitario.add(Double.parseDouble(atribsConcepto.getAttribute("ValorUnitario")));

                if (atribsComprobante.getAttribute("TipoDeComprobante").equals("I")) {
                    descuento.add(Double.parseDouble(atribsConcepto.getAttribute("Descuento")));
                    suma.add(mf.format(valorUnitario.get(i) - descuento.get(i)).replace(",", "."));
                    // String unidad = atribsComprobante.getAttribute("Unidad");
                    conceptoAux = new ConceptoAux(
                            atribsConcepto.getAttribute("Cantidad"),
                            atribsConcepto.getAttribute("ClaveUnidad"),
                            atribsConcepto.getAttribute("Unidad"),
                            atribsConcepto.getAttribute("ClaveProdServ"),
                            atribsConcepto.getAttribute("ObjetoImp"),
                            atribsConcepto.getAttribute("Descripcion"),
                            toFormatMxn(atribsConcepto.getAttribute("ValorUnitario")),
                            toFormatMxn(atribsConcepto.getAttribute("Descuento")),
                            toFormatMxn(suma.get(i)));
                } else {
                    suma.add(mf.format(valorUnitario.get(i)));
                    conceptoAux = new ConceptoAux(
                            atribsConcepto.getAttribute("Cantidad"),
                            atribsConcepto.getAttribute("ClaveUnidad"),
                            null,
                            atribsConcepto.getAttribute("ClaveProdServ"),
                            atribsConcepto.getAttribute("ObjetoImp"),
                            atribsConcepto.getAttribute("Descripcion"),
                            toFormatMxn(atribsConcepto.getAttribute("ValorUnitario")),
                            null,
                            toFormatMxn(suma.get(i)));
                }
                conceptoList.add(conceptoAux);
            }

            NodeList listSat = document.getElementsByTagName("tfd:TimbreFiscalDigital");
            Node nodeSat = listSat.item(0);
            Element atribsSat = (Element) nodeSat;

            parametros.put("csdSat", atribsSat.getAttribute("NoCertificadoSAT"));
            parametros.put("csdEmisor", atribsComprobante.getAttribute("NoCertificado"));
            parametros.put("fechaHoraCertificacion", atribsSat.getAttribute("FechaTimbrado"));
            parametros.put("fechaHoraEmision", atribsSat.getAttribute("FechaTimbrado"));
            parametros.put("folioFiscal", atribsSat.getAttribute("UUID"));
            parametros.put("selloCFDI", atribsSat.getAttribute("SelloCFD"));
            parametros.put("selloSAT", atribsSat.getAttribute("SelloSAT"));

            parametros.put("cadenaOriginal", xmlString.get(0).getCadenaOriginal());

            JRBeanCollectionDataSource conceptosDataSource = new JRBeanCollectionDataSource(conceptoList);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, parametros, conceptosDataSource);

            byte[] reporte = JasperExportManager.exportReportToPdf(jasperPrint);
            String sdf = (new SimpleDateFormat("dd/MM/yyyy")).format(new Date());
            StringBuilder stringBuilder = new StringBuilder().append("InvoicePDF:");

            ContentDisposition contentDisposition = ContentDisposition.builder("attachment")
                    .filename(stringBuilder.append("ARCHIVO")
                            .append("generateDate:")
                            .append(sdf)
                            .append(".pdf")
                            .toString())
                    .build();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentDisposition(contentDisposition);

            // return null;
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .headers(headers).body(new ByteArrayResource(reporte));

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String toFormatMxn(String cantidad) {
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

    public void base64ToJpg(String base64, String uuid) throws Exception {
        try {
            String directorio = System.getProperty("user.dir")
                    + "/src/main/java/com/ceag/facturacion/Archivos/";
            byte[] decodeBytes = Base64.getDecoder().decode(base64);
            Files.write(Paths.get(directorio + uuid + ".png"), decodeBytes);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
