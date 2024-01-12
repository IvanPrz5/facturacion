package com.ceag.facturacion.Service.Facturacion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Facturacion.ConceptoPrecargadoDto;
import com.ceag.facturacion.Entity.Facturacion.ConceptosPrecargadosEntity;
import com.ceag.facturacion.Entity.Facturacion.TrasladosPrecargadosEntity;
import com.ceag.facturacion.Repository.Facturacion.ConceptosPrecargadosRepository;
import com.ceag.facturacion.Repository.Facturacion.TrasladosPrecargadosRepository;
import com.ceag.facturacion.Utils.Facturacion.ConceptosPrecargados;

@Service
public class ConceptosPrecargadosService {

    @Autowired
    ConceptosPrecargadosRepository conceptosPrecargadosRepository;

    @Autowired
    TrasladosPrecargadosRepository trasladosPrecargadosRepository;

    public List<ConceptoPrecargadoDto> getConceptosPre() throws Exception {
        try {
            List<ConceptosPrecargadosEntity> conceptos = conceptosPrecargadosRepository.findAll();
            List<TrasladosPrecargadosEntity> traslados = null;
            
            ConceptosPrecargados conceptosPrecargados = new ConceptosPrecargados();

            List<ConceptoPrecargadoDto> listConceptos = new ArrayList<>();
            listConceptos = conceptosPrecargados.getConceptosPre(conceptos, traslados, trasladosPrecargadosRepository);
            
            return listConceptos;
        } catch (Exception e) {
            throw new Exception("Error datos precargados" + e.getMessage());
        }
    }
}
