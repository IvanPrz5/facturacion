package com.ceag.facturacion.Service.Facturacion;

import org.springframework.http.HttpStatus;

import java.io.ByteArrayInputStream;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ceag.facturacion.Entity.Facturacion.ComprobanteEntity;
import com.ceag.facturacion.Entity.Facturacion.ConceptoEntity;
import com.ceag.facturacion.Repository.Facturacion.ConceptoRepository;
import com.ceag.facturacion.Utils.DatosFactura.DatosFactura;

@Service
public class ConceptoService {
    @Autowired
    ConceptoRepository conceptoRepository;

    @Autowired
    TrasladoService trasladoService;

    public ResponseEntity<Long> addConcepto(byte[] xmlByte, ComprobanteEntity comprobanteEntity, DatosFactura datosFactura){
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(xmlByte));
            NodeList listConceptos = document.getElementsByTagName("cfdi:Concepto");
            
            for(int i=0; i<listConceptos.getLength(); i++){
                Node nodeConcepto = listConceptos.item(i);
                Element atribsConceptos = (Element) nodeConcepto;
                ConceptoEntity conceptoEntity = new ConceptoEntity();
                conceptoEntity.setIdClaveProdServ(atribsConceptos.getAttribute("ClaveProdServ"));
                conceptoEntity.setClaveProdServDesc(datosFactura.getDatosConcepto().get(i).getClaveProdServDesc());
                conceptoEntity.setCantidad(atribsConceptos.getAttribute("Cantidad"));
                conceptoEntity.setIdClaveUnidad(atribsConceptos.getAttribute("ClaveUnidad"));
                conceptoEntity.setUnidad(atribsConceptos.getAttribute("Unidad"));
                conceptoEntity.setDescripcion(atribsConceptos.getAttribute("Descripcion"));
                conceptoEntity.setValorUnitario(Double.parseDouble(atribsConceptos.getAttribute("ValorUnitario")));
                conceptoEntity.setImporte(Double.parseDouble(atribsConceptos.getAttribute("Importe")));
                conceptoEntity.setDescuento(Double.parseDouble(atribsConceptos.getAttribute("Descuento")));
                conceptoEntity.setIdObjetoImp(atribsConceptos.getAttribute("ObjetoImp"));
                conceptoEntity.setStatus(true);
                conceptoEntity.setIdComprobante(comprobanteEntity);

                ConceptoEntity conceptoCreated = conceptoRepository.save(conceptoEntity);

                NodeList listTraslados = atribsConceptos.getElementsByTagName("cfdi:Traslado");

                if(listTraslados.getLength() > 0){
                    trasladoService.addTraslado(datosFactura, i, conceptoCreated);
                }
            }
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el concepto" + e.getMessage());
        }
    }
}
