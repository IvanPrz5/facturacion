package com.ceag.facturacion.Repository.Facturacion;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Facturacion.ComprobanteEntity;
import java.util.Optional;
public interface ComprobanteRepository extends JpaRepository <ComprobanteEntity, Long>{
    Optional<ComprobanteEntity> findByUuidAndStatus(String uuid, Boolean status);
}
