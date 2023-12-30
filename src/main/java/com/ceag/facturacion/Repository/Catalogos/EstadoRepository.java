package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.EstadoEntity;
import com.ceag.facturacion.Entity.Catalogos.PaisEntity;

import java.util.List;

public interface EstadoRepository extends JpaRepository <EstadoEntity, Long> {
    List<EstadoEntity> findByStatus(Boolean status);
    List<EstadoEntity> findByIdPais(PaisEntity idPais);
}
