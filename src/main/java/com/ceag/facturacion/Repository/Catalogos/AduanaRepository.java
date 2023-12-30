package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.AduanaEntity;
import java.util.List;


public interface AduanaRepository extends JpaRepository<AduanaEntity, Long> {
    List<AduanaEntity> findByStatus(Boolean status);
}
