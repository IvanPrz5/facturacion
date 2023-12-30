package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.TipoRelacionEntity;
import java.util.List;

public interface TipoRelacionRepository extends JpaRepository <TipoRelacionEntity, Long> {
    List<TipoRelacionEntity> findByStatus(Boolean status);
}
