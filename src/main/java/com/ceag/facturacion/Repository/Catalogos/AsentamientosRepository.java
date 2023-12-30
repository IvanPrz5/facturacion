package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.AsentamientosEntity;
import java.util.List;


public interface AsentamientosRepository extends JpaRepository<AsentamientosEntity, Long> {
    List<AsentamientosEntity> findByStatus(Boolean status);
}
