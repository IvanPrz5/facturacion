package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.PatenteAduanalEntity;
import java.util.List;

public interface PatenteAduanalRepository extends JpaRepository <PatenteAduanalEntity, Long>{
    List<PatenteAduanalEntity> findByStatus(Boolean status);
}
