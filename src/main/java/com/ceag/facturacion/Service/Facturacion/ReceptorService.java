package com.ceag.facturacion.Service.Facturacion;

import org.springframework.http.HttpStatus;

import java.io.ByteArrayInputStream;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ceag.facturacion.Dto.Xml.ReceptorDto;
import com.ceag.facturacion.Entity.Facturacion.ComprobanteEntity;
import com.ceag.facturacion.Entity.Facturacion.ReceptorEntity;
import com.ceag.facturacion.Repository.Facturacion.ReceptorRepository;

@Service
public class ReceptorService {
    @Autowired
    ReceptorRepository receptorRepository;

    public ReceptorDto getReceptor(String rfc){
        try {
            Optional<ReceptorEntity> receptor = receptorRepository.findByRfc(rfc);
            ReceptorDto receptorDto = new ReceptorDto();
            receptorDto.setNombreReceptor(receptor.get().getNombre());
            receptorDto.setRfcReceptor(receptor.get().getRegimenFiscalReceptor());
            receptorDto.setDomicilioFiscalReceptor(receptor.get().getDomicilioFiscalReceptor());
            receptorDto.setRegimenFiscalReceptor(receptor.get().getRegimenFiscalReceptor());
            receptorDto.setUsoCfdiReceptor(receptor.get().getUsoCfdi());
            return receptorDto;
        } catch (Exception e) {
            throw new IllegalArgumentException("No se obtuvo el receptor " + e.getMessage());
        }
    }

    public ResponseEntity<Long> addReceptor(byte[] xmlByte, ComprobanteEntity comprobanteEntity){
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new ByteArrayInputStream(xmlByte));
            NodeList listReceptor = document.getElementsByTagName("cfdi:Receptor");
            Node nodeReceptor = listReceptor.item(0);
            Element atribsReceptor = (Element) nodeReceptor;

            ReceptorEntity receptorEntity = new ReceptorEntity();
            receptorEntity.setRfc(atribsReceptor.getAttribute("Rfc"));
            receptorEntity.setNombre(atribsReceptor.getAttribute("Nombre"));
            receptorEntity.setDomicilioFiscalReceptor(atribsReceptor.getAttribute("DomicilioFiscalReceptor"));
            receptorEntity.setRegimenFiscalReceptor(atribsReceptor.getAttribute("RegimenFiscalReceptor"));
            receptorEntity.setUsoCfdi(atribsReceptor.getAttribute("UsoCFDI"));
            receptorEntity.setStatus(true);
            receptorEntity.setIdComprobanteEntity(comprobanteEntity);

            receptorRepository.save(receptorEntity);

            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el receptor" + e.getMessage());
        }
    }
}
