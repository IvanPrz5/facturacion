package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.ImpuestoEntity;
import java.util.List;

public interface ImpuestoRepository extends JpaRepository<ImpuestoEntity, Long> {
    List<ImpuestoEntity> findByStatus(Boolean status);
}
