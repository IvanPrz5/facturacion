package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import com.ceag.facturacion.Entity.Catalogos.CodigoPostalEntity;

public interface CodigoPostalRepository extends JpaRepository <CodigoPostalEntity, Long> {
    List<CodigoPostalEntity> findByStatus(Boolean status);
    Optional<CodigoPostalEntity> findById(Long id);
}
