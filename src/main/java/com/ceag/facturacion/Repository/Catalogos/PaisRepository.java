package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.PaisEntity;
import java.util.List;

public interface PaisRepository extends JpaRepository<PaisEntity, Long> {
    List<PaisEntity> findByStatus(Boolean status);
}
