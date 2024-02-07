package com.ceag.facturacion.Repository.Facturacion;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Facturacion.ComprobanteEntity;
import com.ceag.facturacion.Entity.Facturacion.EmisorEntity;
import java.util.List;

public interface EmisorRepository extends JpaRepository <EmisorEntity, Long>{
    // List<EmisorEntity> findByIdComprobanteEntity(ComprobanteEntity idComprobanteEntity);
}