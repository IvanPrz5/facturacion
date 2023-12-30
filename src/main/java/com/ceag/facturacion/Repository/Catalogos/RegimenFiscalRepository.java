package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.RegimenFiscalEntity;
import java.util.List;

public interface RegimenFiscalRepository extends JpaRepository <RegimenFiscalEntity, Long>{
    List<RegimenFiscalEntity> findByStatus(Boolean status);
}
