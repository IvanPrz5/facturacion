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
import com.ceag.facturacion.Entity.Facturacion.EmisorEntity;
import com.ceag.facturacion.Repository.Facturacion.EmisorRepository;

@Service
public class EmisorService {
    @Autowired
    EmisorRepository emisorRepository;

    public ResponseEntity<Long> addEmisor(byte[] xmlByte, ComprobanteEntity comprobanteEntity){
        try {
            
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(xmlByte));
            NodeList listEmisor = document.getElementsByTagName("cfdi:Emisor");
            Node nodeEmisor = listEmisor.item(0);
            Element atribsEmisor = (Element) nodeEmisor;

            EmisorEntity emisorEntity = new EmisorEntity();
            emisorEntity.setRfc(atribsEmisor.getAttribute("Rfc"));
            emisorEntity.setNombre(atribsEmisor.getAttribute("Nombre"));
            emisorEntity.setRegimenFiscal(atribsEmisor.getAttribute("RegimenFiscal"));
            emisorEntity.setStatus(true);
            emisorEntity.setIdComprobanteEntity(comprobanteEntity);
            // emisorEntity.setRfc(atribsEmisor.getAttribute("Rfc"));

            emisorRepository.save(emisorEntity);

            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el emisor" + e.getMessage());
        }
    }
}
