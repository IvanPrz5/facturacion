package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.PeriodicidadEntity;
import java.util.List;

public interface PeriodicidadRepository extends JpaRepository <PeriodicidadEntity, Long> {
    List<PeriodicidadEntity> findByStatus(Boolean status);
}
