package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.LocalidadEntity;
import java.util.List;

public interface LocalidadRepository extends JpaRepository <LocalidadEntity, Long>{
    List<LocalidadEntity> findByStatus(Boolean status);
}
