package com.ceag.facturacion.Repository.Facturacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ceag.facturacion.Dto.Facturacion.CantidadDto;
import com.ceag.facturacion.Entity.Facturacion.ConceptosPrecargadosEntity;

public interface ConceptosPrecargadosRepository extends JpaRepository<ConceptosPrecargadosEntity, Long>{
    
    @Query(value="SELECT cantidad FROM conceptos_precargados", nativeQuery = true)
    List<CantidadDto> Prueba();
}
