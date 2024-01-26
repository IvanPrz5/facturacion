package com.ceag.facturacion.Repository.Facturacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Facturacion.ConceptosPrecargadosEntity;
import com.ceag.facturacion.Entity.Facturacion.ImpuestosPrecargadosEntity;

public interface ImpuestosPrecargadosRepository extends JpaRepository<ImpuestosPrecargadosEntity, Long>{
    List<ImpuestosPrecargadosEntity> findAll();
    List<ImpuestosPrecargadosEntity> findByIdConceptoPrecargado(ConceptosPrecargadosEntity idConceptoPrecargado);
}
