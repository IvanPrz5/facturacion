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

import com.ceag.facturacion.Entity.Facturacion.ConceptoEntity;
import com.ceag.facturacion.Entity.Facturacion.TrasladoEntity;
import com.ceag.facturacion.Repository.Facturacion.TrasladoRepository;
import com.ceag.facturacion.Utils.DatosFactura.DatosFactura;

@Service
public class TrasladoService {
    @Autowired
    TrasladoRepository trasladoRepository;

    public ResponseEntity<Long> addTraslado(byte[] xmlByte, Document document, NodeList listTraslados, DatosFactura datosFactura, int h, ConceptoEntity conceptoEntity){
        try {
            
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(xmlByte));
            // listConceptos = document.getElementsByTagName("cfdi:Traslado");
            for(int i=0; i<listTraslados.getLength(); i++){
                Node nodeTraslado = listTraslados.item(i);
                Element atribsTraslados = (Element) nodeTraslado;
                TrasladoEntity trasladoEntity = new TrasladoEntity();
                trasladoEntity.setBase(atribsTraslados.getAttribute("Base"));
                trasladoEntity.setImpuesto(atribsTraslados.getAttribute("Impuesto"));
                trasladoEntity.setTipoFactor(atribsTraslados.getAttribute("TipoFactor"));
                trasladoEntity.setTasaCuota(atribsTraslados.getAttribute("TasaCuota"));
                trasladoEntity.setImporte(atribsTraslados.getAttribute("Importe"));
                trasladoEntity.setIsTrasladado(datosFactura.getDatosConcepto().get(h).getDatosImpuesto().get(i).getIsTrasladado());
                trasladoEntity.setStatus(true);
                trasladoEntity.setIdConceptoEntity(conceptoEntity);

                trasladoRepository.save(trasladoEntity);
            }

            // trasladoRepository.save(traslado);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el traslado" + e.getMessage());
        }
    }
}
