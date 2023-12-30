package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.MunicipioEntity;
import java.util.List;

public interface MunicipioRepository extends JpaRepository <MunicipioEntity, Long> {
    List<MunicipioEntity> findByStatus(Boolean status);
}
