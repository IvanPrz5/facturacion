package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.MonedaEntity;
import java.util.List;

public interface MonedaRepository extends JpaRepository <MonedaEntity, Long> {
    List<MonedaEntity> findByStatus(Boolean status);
}
