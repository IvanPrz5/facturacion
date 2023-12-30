package com.ceag.facturacion.Service.Facturacion;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Entity.Facturacion.ConceptoEntity;
import com.ceag.facturacion.Repository.Facturacion.ConceptoRepository;

@Service
public class ConceptoService {
    @Autowired
    ConceptoRepository conceptoRepository;

    public ResponseEntity<Long> addConcepto(ConceptoEntity concepto){
        try {
            conceptoRepository.save(concepto);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el concepto" + e.getMessage());
        }
    }
}
