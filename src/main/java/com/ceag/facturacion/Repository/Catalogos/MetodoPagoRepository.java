package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.MetodoPagoEntity;
import java.util.List;

public interface MetodoPagoRepository extends JpaRepository <MetodoPagoEntity, Long> {
    List<MetodoPagoEntity> findByStatus(Boolean status);
}
