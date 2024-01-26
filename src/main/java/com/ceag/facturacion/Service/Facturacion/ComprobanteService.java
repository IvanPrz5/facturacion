package com.ceag.facturacion.Service.Facturacion;

import com.ceag.facturacion.Utils.DatosFactura.DatosFactura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Entity.Facturacion.ComprobanteEntity;
import com.ceag.facturacion.Repository.Facturacion.ComprobanteRepository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;

@Service
public class ComprobanteService {
    @Autowired
    ComprobanteRepository comprobanteRepository;

    @Autowired
    EmisorService emisorService;

    @Autowired
    ReceptorService receptorService;

    @Autowired
    ConceptoService conceptoService;


    public ResponseEntity<Long> addComprobante(String xml, DatosFactura datosFactura){
        try {
            byte[] xmlByte = xml.getBytes();

            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(xmlByte));
            NodeList listComprobante = document.getElementsByTagName("cfdi:Comprobante");
            Node nodeComprobante = listComprobante.item(0);
            Element atribsComprobante = (Element) nodeComprobante;
            
            ComprobanteEntity comprobanteEntity = new ComprobanteEntity();
            comprobanteEntity.setTipoComprobante(atribsComprobante.getAttribute("TipoDeComprobante"));
            comprobanteEntity.setExportacion(atribsComprobante.getAttribute("Exportacion"));
            comprobanteEntity.setMetodoPago(atribsComprobante.getAttribute("MetodoPago"));
            comprobanteEntity.setFormaPago(atribsComprobante.getAttribute("FormaPago"));
            comprobanteEntity.setLugarExpedicion(atribsComprobante.getAttribute("LugarExpedicion"));
            comprobanteEntity.setFecha(LocalDateTime.parse(atribsComprobante.getAttribute("Fecha")));
            comprobanteEntity.setMoneda(atribsComprobante.getAttribute("Moneda"));
            comprobanteEntity.setSubTotal(Double.parseDouble(atribsComprobante.getAttribute("SubTotal")));
            comprobanteEntity.setTotal(Double.parseDouble(atribsComprobante.getAttribute("Total")));
            comprobanteEntity.setDescuento(Double.parseDouble(atribsComprobante.getAttribute("Descuento")));
            // Aqui va un if con el uuid cuando se vaya a timbrar despues
            if(datosFactura.getDatosComprobante().getIsTimbrado()){
                NodeList listTimbre = document.getElementsByTagName("tfd:TimbreFiscalDigital");
                Node nodeTimbre = listTimbre.item(0);
                Element atribsTimbre = (Element) nodeTimbre;
                comprobanteEntity.setUuid(atribsTimbre.getAttribute("UUID"));
            }else{
                comprobanteEntity.setUuid("000000000");
            }
            comprobanteEntity.setIsTimbrado(datosFactura.getDatosComprobante().getIsTimbrado());
            comprobanteEntity.setStatus(true);

            ComprobanteEntity comprobanteCreated = comprobanteRepository.save(comprobanteEntity);

            if(comprobanteCreated.getId() != null){
                emisorService.addEmisor(xmlByte, comprobanteCreated);
                receptorService.addReceptor(xmlByte, comprobanteCreated);
                conceptoService.addConcepto(xmlByte, comprobanteCreated, datosFactura);
            }

            return new ResponseEntity<>(comprobanteCreated.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el comprobante: " + e.getMessage());
        }
    }
}
