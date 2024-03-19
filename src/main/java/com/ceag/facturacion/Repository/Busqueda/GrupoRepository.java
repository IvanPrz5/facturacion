package com.ceag.facturacion.Repository.Busqueda;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Busqueda.GruposEntity;
import java.util.List;

public interface GrupoRepository extends JpaRepository<GruposEntity, Long>{
    List<GruposEntity> findByDivision(String division);
}
