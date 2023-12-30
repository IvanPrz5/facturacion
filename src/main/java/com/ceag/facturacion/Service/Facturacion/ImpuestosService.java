package com.ceag.facturacion.Service.Facturacion;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Entity.Facturacion.ImpuestosEntity;
import com.ceag.facturacion.Repository.Facturacion.ImpuestosRepository;

@Service
public class ImpuestosService {
    @Autowired
    ImpuestosRepository impuestosRepository;

    public ResponseEntity<Long> addImpuestos(ImpuestosEntity impuestos){
        try {
            impuestosRepository.save(impuestos);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el impuesto" + e.getMessage());
        }
    }
}
