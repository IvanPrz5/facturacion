package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.ClaveUnidadEntity;
import java.util.List;

public interface ClaveUnidadRepository extends JpaRepository <ClaveUnidadEntity, Long> {
    List<ClaveUnidadEntity> findByStatus(Boolean status);
    List<ClaveUnidadEntity> findByCodigoAndStatus(String codigo, Boolean status);
    List<ClaveUnidadEntity> findByNombreContaining(String descripcion);
}
