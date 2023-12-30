package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.ExportacionEntity;
import java.util.List;

public interface ExportacionRepository extends JpaRepository <ExportacionEntity, Long> {
    List<ExportacionEntity> findByStatus(Boolean status);
}
