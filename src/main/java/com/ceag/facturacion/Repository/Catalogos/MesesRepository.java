package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.MesesEntity;
import java.util.List;

public interface MesesRepository extends JpaRepository <MesesEntity, Long> {
    List<MesesEntity> findByStatus(Boolean status);
}
