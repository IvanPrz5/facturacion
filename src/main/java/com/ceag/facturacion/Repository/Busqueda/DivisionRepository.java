package com.ceag.facturacion.Repository.Busqueda;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Busqueda.DivisionEntity;

public interface DivisionRepository extends JpaRepository<DivisionEntity, Long>{
    List<DivisionEntity> findAllByOrderByClaveAsc();
    List<DivisionEntity> findByProductoServicioOrderByClave(Boolean productoServicio);
}
