package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.TipoFactorEntity;
import java.util.List;

public interface TipoFactorRepository extends JpaRepository <TipoFactorEntity, Long> {
    List<TipoFactorEntity> findByStatus(Boolean status);
}
