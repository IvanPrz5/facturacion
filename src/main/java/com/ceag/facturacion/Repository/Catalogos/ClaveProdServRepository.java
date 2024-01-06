package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.ClaveProdServEntity;
import java.util.List;

public interface ClaveProdServRepository extends JpaRepository <ClaveProdServEntity, Long> {
    List<ClaveProdServEntity> findByStatus(Boolean status);
    List<ClaveProdServEntity> findByCodigoAndStatus(String codigo, Boolean status);
    List<ClaveProdServEntity> findByDescripcionContaining(String descripcion);
}
