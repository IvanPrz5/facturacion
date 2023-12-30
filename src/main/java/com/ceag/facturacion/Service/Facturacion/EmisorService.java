package com.ceag.facturacion.Service.Facturacion;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Entity.Facturacion.EmisorEntity;
import com.ceag.facturacion.Repository.Facturacion.EmisorRepository;

@Service
public class EmisorService {
    @Autowired
    EmisorRepository emisorRepository;

    public ResponseEntity<Long> addEmisor(EmisorEntity emisor){
        try {
            emisorRepository.save(emisor);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el emisor" + e.getMessage());
        }
    }
}
