package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.TipoComprobanteEntity;
import java.util.List;

public interface TipoComprobanteRepository extends JpaRepository <TipoComprobanteEntity, Long>{
    List<TipoComprobanteEntity> findByStatus(Boolean status);
}
