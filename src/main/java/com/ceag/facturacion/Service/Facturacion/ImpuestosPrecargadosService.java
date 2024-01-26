package com.ceag.facturacion.Service.Facturacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Entity.Facturacion.ImpuestosPrecargadosEntity;
import com.ceag.facturacion.Repository.Facturacion.ImpuestosPrecargadosRepository;

@Service
public class ImpuestosPrecargadosService {
    @Autowired
    ImpuestosPrecargadosRepository trasladosPrecargadosRepository;

    public ResponseEntity<List<ImpuestosPrecargadosEntity>> getTrasladosPrecargados() throws Exception{
        try {
            List<ImpuestosPrecargadosEntity> trasladosPrecargados = trasladosPrecargadosRepository.findAll();
            return new ResponseEntity<>(trasladosPrecargados, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception("Error Traslados Precargados" + e.getMessage());
        }
    }    
}
