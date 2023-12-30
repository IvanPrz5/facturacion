package com.ceag.facturacion.Service.Facturacion;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Entity.Facturacion.ReceptorEntity;
import com.ceag.facturacion.Repository.Facturacion.ReceptorRepository;

@Service
public class ReceptorService {
    @Autowired
    ReceptorRepository receptorRepository;

    public ResponseEntity<Long> addReceptor(ReceptorEntity receptor){
        try {
            receptorRepository.save(receptor);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el receptor" + e.getMessage());
        }
    }
}
