package com.ceag.facturacion.Service.Facturacion;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Entity.Facturacion.TrasladoEntity;
import com.ceag.facturacion.Repository.Facturacion.TrasladoRepository;

@Service
public class TrasladoService {
    @Autowired
    TrasladoRepository trasladoRepository;

    public ResponseEntity<Long> addComprobante(TrasladoEntity traslado){
        try {
            trasladoRepository.save(traslado);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el traslado" + e.getMessage());
        }
    }
}
