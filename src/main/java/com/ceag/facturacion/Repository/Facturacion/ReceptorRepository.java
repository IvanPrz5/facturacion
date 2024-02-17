package com.ceag.facturacion.Repository.Facturacion;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Facturacion.ReceptorEntity;
import java.util.Optional;

public interface ReceptorRepository extends JpaRepository <ReceptorEntity, Long>{
    Optional<ReceptorEntity> findByRfc(String rfc);
}