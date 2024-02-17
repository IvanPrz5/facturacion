package com.ceag.facturacion.Repository.Facturacion;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Facturacion.XmlEntity;
import java.util.List;

public interface XmlRepository extends JpaRepository <XmlEntity, Long>{
    List<XmlEntity> findByUuid(String uuid);
}
