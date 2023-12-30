package com.ceag.facturacion.Service.Facturacion;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Entity.Facturacion.ComprobanteEntity;
import com.ceag.facturacion.Repository.Facturacion.ComprobanteRepository;

@Service
public class ComprobanteService {
    @Autowired
    ComprobanteRepository comprobanteRepository;

    public ResponseEntity<Long> addComprobante(ComprobanteEntity comprobante){
        try {
            comprobanteRepository.save(comprobante);
            return new ResponseEntity<>(comprobante.getId(), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el comprobante" + e.getMessage());
        }
    }
}
