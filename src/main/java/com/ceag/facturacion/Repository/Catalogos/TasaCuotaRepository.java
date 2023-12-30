package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.TasaCuotaEntity;
import java.util.List;

public interface TasaCuotaRepository extends JpaRepository <TasaCuotaEntity, Long>{
    List<TasaCuotaEntity> findByStatus(Boolean status);
}
