package com.ceag.facturacion.Utils.Facturacion;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ceag.facturacion.Dto.Facturacion.PagosDto;
import com.ceag.facturacion.Entity.Empresas.EmpresasEntity;
import com.ceag.facturacion.Repository.Empresas.EmpresasRepository;
import com.ceag.facturacion.Utils.DatosFactura.DatosPPD;
import com.ceag.facturacion.Utils.DatosFacturacion.DatosFacturacionCeag;
import com.ceag.facturacion.Utils.DatosFacturacion.FacturacionCeagStatus;

public class NodosXmlPagos {

    @Autowired
    EmpresasRepository empresasRepository;
    
    public DatosPPD formarXmlPPD(PagosDto pagosDto) throws Exception {
        
        DatosPPD datosPPD = new DatosPPD();
        String xmlString = "";

        DatosFacturacionCeag datosFacturacionCeag = new DatosFacturacionCeag(FacturacionCeagStatus.TIPO_PRODUCCION);

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = dbf.newDocumentBuilder();
        Document document = documentBuilder.newDocument();

        String nameSpace = "http://www.sat.gob.mx/cfd/4";
        String prefijo = "cfdi:";

        String nameSpacePagos ="http://www.sat.gob.mx/sitio_internet/cfd/4/cfdv40.xsd http://www.sat.gob.mx/Pagos20";
        String prefijoPagos ="pago20:";

        DecimalFormat df = new DecimalFormat("0.00");

        Element comprobante = document.createElementNS(nameSpace, prefijo + "Comprobante");
        document.appendChild(comprobante);
        comprobante.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        comprobante.setAttribute("xmlns:implocal", "http://www.sat.gob.mx/implocal");
        comprobante.setAttribute("xsi:schemaLocation", "http://www.sat.gob.mx/cfd/4 http://www.sat.gob.mx/sitio_internet/cfd/4/cfdv40.xsd http://www.sat.gob.mx/Pagos20 http://www.sat.gob.mx/sitio_internet/cfd/Pagos/Pagos20.xsd http://www.sat.gob.mx/implocal http://www.sat.gob.mx/sitio_internet/cfd/implocal/implocal.xsd");
        comprobante.setAttribute("Version", "4.0");

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

        LocalDateTime fechaActual = java.time.LocalDateTime.now();
        int x = fechaActual.toString().indexOf(".");

        Optional<EmpresasEntity> empresa = empresasRepository.findById(datosPPD.getIdEmpresa());

        comprobante.setAttribute("Fecha", formatter.format(dateWithTimeZone).replace(" ", "T"));

        comprobante.setAttribute("NoCertificado", empresa.get().getCerB64());
        comprobante.setAttribute("Certificado", empresa.get().getNumCertificado());

        comprobante.setAttribute("SubTotal", "0");
        comprobante.setAttribute("Sello", "");
        comprobante.setAttribute("Moneda", "XXX");

        comprobante.setAttribute("Folio", "REVISAR FOLIO ACA -------------");
        comprobante.setAttribute("Total", "0");
        comprobante.setAttribute("TipoDeComprobante", "P");
        comprobante.setAttribute("Exportacion", "01");
        comprobante.setAttribute("LugarExpedicion", empresa.get().getCodPostal());

        Element emisor = document.createElement(prefijo + "Emisor");
        comprobante.appendChild(emisor);

        emisor.setAttribute("Rfc", empresa.get().getRfc());
        emisor.setAttribute("Nombre", empresa.get().getNombre());
        emisor.setAttribute("RegimenFiscal", empresa.get().getIdRegimenFiscal().getCodigo());

        Element receptor = document.createElement(prefijo + "Receptor");
        comprobante.appendChild(receptor);

        receptor.setAttribute("Rfc", datosPPD.getDatosReceptor().getRfc());
        receptor.setAttribute("Nombre", datosPPD.getDatosReceptor().getNombre());
        receptor.setAttribute("DomicilioFiscalReceptor", datosPPD.getDatosReceptor().getDomicilioFiscal());
        receptor.setAttribute("RegimenFiscalReceptor", datosPPD.getDatosReceptor().getRegimenFiscal());
        receptor.setAttribute("UsoCFDI", datosPPD.getDatosReceptor().getUsoCfdi());

        Element conceptos = document.createElement(prefijo + "Conceptos");
        comprobante.appendChild(conceptos);

        for(int i=0; i<1; i++){
            Element concepto = document.createElement(prefijo + "Concepto");
            conceptos.setAttribute("ClaveProdServ", "84111506");
        }
        return null;
    }
}
