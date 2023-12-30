package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.ObjetoImpEntity;
import java.util.List;

public interface ObjetoImpRepository extends JpaRepository <ObjetoImpEntity, Long> {
    List<ObjetoImpEntity> findByStatus(Boolean status);
}
