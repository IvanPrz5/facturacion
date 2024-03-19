package com.ceag.facturacion.Repository.Facturacion;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Facturacion.ConceptosPrecargadosEntity;
import com.ceag.facturacion.Entity.Empresas.EmpresasEntity;


public interface ConceptosPrecargadosRepository extends JpaRepository<ConceptosPrecargadosEntity, Long>{
    List<ConceptosPrecargadosEntity> findByIdEmpresaOrderByFechaCreacionDesc(EmpresasEntity idEmpresa);
}
