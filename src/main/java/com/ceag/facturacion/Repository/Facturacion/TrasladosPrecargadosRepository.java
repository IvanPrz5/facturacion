package com.ceag.facturacion.Repository.Facturacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Facturacion.ConceptosPrecargadosEntity;
import com.ceag.facturacion.Entity.Facturacion.TrasladosPrecargadosEntity;

public interface TrasladosPrecargadosRepository extends JpaRepository<TrasladosPrecargadosEntity, Long>{
    List<TrasladosPrecargadosEntity> findAll();
    List<TrasladosPrecargadosEntity> findByIdConceptoPrecargado(ConceptosPrecargadosEntity idConceptoPrecargado);
}
