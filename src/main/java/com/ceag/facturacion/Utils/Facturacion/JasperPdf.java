package com.ceag.facturacion.Utils.Facturacion;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ceag.facturacion.Entity.Catalogos.CodigoPostalEntity;
import com.ceag.facturacion.Entity.Catalogos.FormaPagoEntity;
import com.ceag.facturacion.Entity.Catalogos.MetodoPagoEntity;
import com.ceag.facturacion.Entity.Catalogos.RegimenFiscalEntity;
import com.ceag.facturacion.Entity.Catalogos.UsoCFDIEntity;
import com.ceag.facturacion.Entity.Empresas.EmpresasEntity;
import com.ceag.facturacion.Entity.Facturacion.XmlEntity;
import com.ceag.facturacion.Repository.Catalogos.CodigoPostalRepository;
import com.ceag.facturacion.Repository.Catalogos.FormaPagoRepository;
import com.ceag.facturacion.Repository.Catalogos.MetodoPagoRepository;
import com.ceag.facturacion.Repository.Catalogos.RegimenFiscalRepository;
import com.ceag.facturacion.Repository.Catalogos.UsoCFDIRepository;
import com.ceag.facturacion.Repository.Empresas.EmpresasRepository;
import com.ceag.facturacion.Repository.Facturacion.XmlRepository;
import com.ceag.facturacion.Utils.DatosFactura.ConceptoAux;
import com.ceag.facturacion.Utils.DatosFactura.DatosFactura;
import com.ceag.facturacion.Utils.DatosFactura.DatosImpuesto;
import com.ceag.facturacion.Utils.DatosFactura.LocalesAux;

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
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;

@Service
public class JasperPdf {
    @Autowired
    XmlRepository xmlRepository;

    @Autowired
    EmpresasRepository empresasRepository;

    @Autowired
    CodigoPostalRepository codigoPostalRepository;

    @Autowired
    MetodoPagoRepository metodoPagoRepository;

    @Autowired
    FormaPagoRepository formaPagoRepository;

    @Autowired
    UsoCFDIRepository usoCFDIRepository;

    @Autowired
    RegimenFiscalRepository regimenFiscalRepository;

    public byte[] crearPdf(String uuid, Long idEmpresa, DatosFactura datosFactura) throws Exception {
        try {

            Optional<EmpresasEntity> empresa = empresasRepository.findById(idEmpresa);
            Optional<CodigoPostalEntity> codPostal = codigoPostalRepository
                .findById(Long.parseLong(empresa.get().getCodPostal()));
            
            InputStream xml = null;
            List<XmlEntity> xmlString = new ArrayList<>();
            if(!uuid.equals("XXXXXXXXXX")){
                xmlString = xmlRepository.findByUuid(uuid);
                xml = new ByteArrayInputStream(xmlString.get(0).getXmlString().getBytes());
            }else{
                NodosXml nodosXml = new NodosXml();
                String xmlString2 = nodosXml.xmlComprobanteAndNodos(datosFactura, empresa);
                xml = new ByteArrayInputStream(xmlString2.getBytes());
            }
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(xml);
            document.getDocumentElement().normalize();

            Map<String, Object> parametros = new HashMap<>();
            
            InputStream isJasper = null;
            if(!uuid.equals("XXXXXXXXXX")){
                isJasper = getFileFromResourceAsStream("cfdi/jasper/comprobanteFiscal.jrxml");
            }else{
                isJasper = getFileFromResourceAsStream("cfdi/jasper/VistaPrevia.jrxml");
            }
            
            JasperReport jasper = JasperCompileManager.compileReport(isJasper);
            if(datosFactura.getDatosLocales().size() > 0){
                InputStream isJasper2 = getFileFromResourceAsStream("cfdi/jasper/subReport.jrxml");
                JasperReport jasper2 = JasperCompileManager.compileReport(isJasper2);
    
                parametros.put("subReport", jasper2);
            }

            NodeList listComprobante = document.getElementsByTagName("cfdi:Comprobante");
            Node nodeComprobante = listComprobante.item(0);
            Element atribsComprobante = (Element) nodeComprobante;

            if(empresa.get().getLogo() != null){
                byte[] logo = Base64.getDecoder().decode(empresa.get().getLogo().getBytes());
                parametros.put("image", new ByteArrayInputStream(logo));
            }

            if(!uuid.equals("XXXXXXXXXX")){
                byte[] qr = Base64.getDecoder().decode(xmlString.get(0).getCvv().getBytes());
                parametros.put("qr", new ByteArrayInputStream(qr));
            }

            if(uuid.equals("XXXXXXXXXX")){
                FondoB64 fondoClass = new FondoB64();
                byte[] fondo = Base64.getDecoder().decode(fondoClass.getFondoB64().getBytes());
                parametros.put("fondo", new ByteArrayInputStream(fondo));
            }

            Optional<MetodoPagoEntity> metodoPago = metodoPagoRepository
                    .findByCodigo(atribsComprobante.getAttribute("MetodoPago"));
            Optional<FormaPagoEntity> formaPago = formaPagoRepository
                    .findByCodigo(atribsComprobante.getAttribute("FormaPago"));

            parametros.put("metodoPago", metodoPago.get().getCodigo() + " - " + metodoPago.get().getDescripcion());
            parametros.put("moneda", "MXN");
            parametros.put("codPago", formaPago.get().getCodigo());
            parametros.put("formaPago", formaPago.get().getDescripcion());

            parametros.put("subTotalSuma", toFormatMxn(atribsComprobante.getAttribute("SubTotal")));
            parametros.put("descuentoSuma", toFormatMxn(atribsComprobante.getAttribute("Descuento")));
            parametros.put("totalSuma", toFormatMxn(atribsComprobante.getAttribute("Total")));

            if (atribsComprobante.getAttribute("Folio") != "") {
                parametros.put("folioFacturar", "FOLIO" + "\n" + atribsComprobante.getAttribute("Folio"));
            }
            parametros.put("lugarExpedicionEmpresa",
                    codPostal.get().getId() + ".- " + codPostal.get().getIdMunicipio().getDescripcion() + ", "
                            + codPostal.get().getIdEstado().getNombreEstado() + ", "
                            + codPostal.get().getIdEstado().getCodigo());

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
            List<RegimenFiscalEntity> regimenFiscal = regimenFiscalRepository.findByCodigo(atribsReceptor.getAttribute("RegimenFiscalReceptor"));
            parametros.put("codRegimenFiscal", atribsReceptor.getAttribute("RegimenFiscalReceptor"));
            parametros.put("regimenFiscal", regimenFiscal.get(0).getDescripcion());

            Optional<UsoCFDIEntity> usoCfdi = usoCFDIRepository.findByCodigo(atribsReceptor.getAttribute("UsoCFDI"));
            parametros.put("uso", atribsReceptor.getAttribute("UsoCFDI"));
            parametros.put("usoLetra", usoCfdi.get().getDescripcion());

            // Conceptos
            List<ConceptoAux> conceptoList = new ArrayList<>();
            List<Double> valorUnitario = new ArrayList<>();
            List<Double> descuento = new ArrayList<>();
            List<String> suma = new ArrayList<>();
            DecimalFormat mf = new DecimalFormat("0.00");
            mf.setMinimumFractionDigits(2);
            ConceptoAux conceptoAux = null;

            Double totalRetenciones = 0.00;
            Double totalTrasladados = 0.00;
            for (int i = 0; i < datosFactura.getDatosConcepto().size(); i++) {
                valorUnitario.add(datosFactura.getDatosConcepto().get(i).getValorUnitario());
                for (int j = 0; j < datosFactura.getDatosConcepto().get(i).getDatosImpuesto().size(); j++) {
                    if (datosFactura.getDatosConcepto().get(i).getDatosImpuesto().get(j).getIsTrasladado()) {
                        totalTrasladados += datosFactura.getDatosConcepto().get(i).getDatosImpuesto().get(j)
                                .getImporte();
                    } else {
                        totalRetenciones += datosFactura.getDatosConcepto().get(i).getDatosImpuesto().get(j)
                                .getImporte();
                    }
                }
                List<String> impuesto = datosFactura.getDatosConcepto().get(i).getDatosImpuesto().stream()
                        .collect(Collectors.mapping(DatosImpuesto::getImpuesto, Collectors.toList()));
                List<String> tasaCuota = datosFactura.getDatosConcepto().get(i).getDatosImpuesto().stream()
                        .collect(Collectors.mapping(DatosImpuesto::getTasaCuota, Collectors.toList()));
                List<String> tipoFactor = datosFactura.getDatosConcepto().get(i).getDatosImpuesto().stream()
                        .collect(Collectors.mapping(DatosImpuesto::getTipoFactor, Collectors.toList()));
                List<Double> base = datosFactura.getDatosConcepto().get(i).getDatosImpuesto().stream()
                        .collect(Collectors.mapping(DatosImpuesto::getBase, Collectors.toList()));
                List<Double> importe = datosFactura.getDatosConcepto().get(i).getDatosImpuesto().stream()
                        .collect(Collectors.mapping(DatosImpuesto::getImporte, Collectors.toList()));
                List<Boolean> tipoImpuesto = datosFactura.getDatosConcepto().get(i).getDatosImpuesto().stream()
                        .collect(Collectors.mapping(DatosImpuesto::getIsTrasladado, Collectors.toList()));

                if (atribsComprobante.getAttribute("TipoDeComprobante").equals("I")) {
                    descuento.add(datosFactura.getDatosConcepto().get(i).getDescuento());
                    suma.add(mf.format(valorUnitario.get(i) - descuento.get(i)).replace(",", "."));

                    if (datosFactura.getDatosConcepto().get(i).getDatosImpuesto().size() == 0) {
                        conceptoAux = new ConceptoAux(
                                datosFactura.getDatosConcepto().get(i).getCantidad(),
                                datosFactura.getDatosConcepto().get(i).getIdClaveUnidad(),
                                datosFactura.getDatosConcepto().get(i).getUnidad(),
                                datosFactura.getDatosConcepto().get(i).getIdClaveProdServ(),
                                datosFactura.getDatosConcepto().get(i).getIdObjetoImp(),
                                datosFactura.getDatosConcepto().get(i).getDescripcion(),
                                toFormatMxn(datosFactura.getDatosConcepto().get(i).getValorUnitario().toString()),
                                toFormatMxn(datosFactura.getDatosConcepto().get(i).getDescuento().toString()),
                                toFormatMxn(suma.get(i)),
                                null,
                                null,
                                null,
                                null,
                                null,
                                null);
                    } else {
                        conceptoAux = new ConceptoAux(
                                datosFactura.getDatosConcepto().get(i).getCantidad(),
                                datosFactura.getDatosConcepto().get(i).getIdClaveUnidad(),
                                datosFactura.getDatosConcepto().get(i).getUnidad(),
                                datosFactura.getDatosConcepto().get(i).getIdClaveProdServ(),
                                datosFactura.getDatosConcepto().get(i).getIdObjetoImp(),
                                datosFactura.getDatosConcepto().get(i).getDescripcion(),
                                toFormatMxn(datosFactura.getDatosConcepto().get(i).getValorUnitario().toString()),
                                toFormatMxn(datosFactura.getDatosConcepto().get(i).getDescuento().toString()),
                                toFormatMxn(suma.get(i)),
                                impuesto.toString(),
                                tasaCuota.toString().replace("[", " ").replace("]", ""),
                                tipoFactor.toString().replace("[", " ").replace("]", ""),
                                base.toString().replace("[", " ").replace("]", ""),
                                importe.toString().replace("[", " ").replace("]", ""),
                                tipoImpuesto.toString());
                    }
                } else {
                    suma.add(mf.format(valorUnitario.get(i)));

                    if (datosFactura.getDatosConcepto().get(i).getDatosImpuesto().size() == 0) {
                        conceptoAux = new ConceptoAux(
                                datosFactura.getDatosConcepto().get(i).getCantidad(),
                                datosFactura.getDatosConcepto().get(i).getIdClaveUnidad(),
                                null,
                                datosFactura.getDatosConcepto().get(i).getIdClaveProdServ(),
                                datosFactura.getDatosConcepto().get(i).getIdObjetoImp(),
                                datosFactura.getDatosConcepto().get(i).getDescripcion(),
                                toFormatMxn(datosFactura.getDatosConcepto().get(i).getValorUnitario().toString()),
                                null,
                                toFormatMxn(suma.get(i)),
                                null,
                                null,
                                null,
                                null,
                                null,
                                null);
                    } else {
                        conceptoAux = new ConceptoAux(
                                datosFactura.getDatosConcepto().get(i).getCantidad(),
                                datosFactura.getDatosConcepto().get(i).getIdClaveUnidad(),
                                null,
                                datosFactura.getDatosConcepto().get(i).getIdClaveProdServ(),
                                datosFactura.getDatosConcepto().get(i).getIdObjetoImp(),
                                datosFactura.getDatosConcepto().get(i).getDescripcion(),
                                toFormatMxn(datosFactura.getDatosConcepto().get(i).getValorUnitario().toString()),
                                null,
                                toFormatMxn(suma.get(i)),
                                impuesto.toString(),
                                tasaCuota.toString().replace("[", " ").replace("]", ""),
                                tipoFactor.toString().replace("[", " ").replace("]", ""),
                                base.toString().replace("[", " ").replace("]", ""),
                                importe.toString().replace("[", " ").replace("]", ""),
                                tipoImpuesto.toString());
                    }
                }
                conceptoList.add(conceptoAux);
            }

            
            if(datosFactura.getDatosLocales().size() > 0){
                LocalesAux localesAux = null;
                Collection<LocalesAux> listLocales = new ArrayList<>();
                for(int i=0; i<datosFactura.getDatosLocales().size(); i++){
                    localesAux = new LocalesAux(
                        datosFactura.getDatosLocales().get(i).getImpuesto(), 
                        toFormatMxn(datosFactura.getDatosLocales().get(i).getTasaCuota().toString()), 
                        toFormatMxn(datosFactura.getDatosLocales().get(i).getImporte().toString()));
                    listLocales.add(localesAux);
                }
                System.out.println("Entor aca mero slasdjnlaksd");
                parametros.put("impLocales", listLocales);
            }

            if (totalTrasladados > 0.00) {
                parametros.put("impTrasladados", toFormatMxn(totalTrasladados.toString()));
            }
            if (totalRetenciones > 0.00) {
                parametros.put("impRetenidos", toFormatMxn(totalRetenciones.toString()));
            }

            if(!uuid.equals("XXXXXXXXXX")){
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
            }
            

            if(!uuid.equals("XXXXXXXXXX")){
                parametros.put("cadenaOriginal", xmlString.get(0).getCadenaOriginal());
            }

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

            return reporte;
            /* return ResponseEntity.ok()
             * .contentType(MediaType.APPLICATION_PDF)
             * .headers(headers).body(new ByteArrayResource(reporte));
             */
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
