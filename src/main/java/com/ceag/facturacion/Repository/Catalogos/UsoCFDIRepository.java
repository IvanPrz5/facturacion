package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.UsoCFDIEntity;
import java.util.List;

public interface UsoCFDIRepository extends JpaRepository <UsoCFDIEntity, Long>{ 
    List<UsoCFDIEntity> findByStatus(Boolean status);
}
