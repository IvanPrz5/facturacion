package com.ceag.facturacion.Service.Facturacion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Facturacion.ConceptoPrecargadoDto;
import com.ceag.facturacion.Entity.Facturacion.ConceptosPrecargadosEntity;
import com.ceag.facturacion.Entity.Facturacion.ImpuestosPrecargadosEntity;
import com.ceag.facturacion.Repository.Facturacion.ConceptosPrecargadosRepository;
import com.ceag.facturacion.Repository.Facturacion.ImpuestosPrecargadosRepository;
import com.ceag.facturacion.Utils.Facturacion.ConceptosPrecargados;

@Service
public class ConceptosPrecargadosService {

    @Autowired
    ConceptosPrecargadosRepository conceptosPrecargadosRepository;

    @Autowired
    ImpuestosPrecargadosRepository trasladosPrecargadosRepository;

    public List<ConceptoPrecargadoDto> getConceptosPre() throws Exception {
        try {
            List<ConceptosPrecargadosEntity> conceptos = conceptosPrecargadosRepository.findAll();
            List<ImpuestosPrecargadosEntity> traslados = null;
            
            ConceptosPrecargados conceptosPrecargados = new ConceptosPrecargados();

            List<ConceptoPrecargadoDto> listConceptos = new ArrayList<>();
            listConceptos = conceptosPrecargados.getConceptosPre(conceptos, traslados, trasladosPrecargadosRepository);
            
            return listConceptos;
        } catch (Exception e) {
            throw new Exception("Error datos precargados" + e.getMessage());
        }
    }

    public ResponseEntity<ConceptosPrecargadosEntity> addRegister(ConceptosPrecargadosEntity concepto) {
        try {
            conceptosPrecargadosRepository.save(concepto);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se agrego el concepto" + e.getMessage());
        }       
    }
}
