package com.ceag.facturacion.Repository.Empresas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Empresas.EmpresasEntity;

public interface EmpresasRepository extends JpaRepository <EmpresasEntity, Long> {
    List<EmpresasEntity> findByIdAndStatus(Long id, Boolean status);
    List<EmpresasEntity> findByStatus(Boolean status);
}