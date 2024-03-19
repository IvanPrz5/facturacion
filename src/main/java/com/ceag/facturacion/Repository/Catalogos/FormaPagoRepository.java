package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.FormaPagoEntity;
import java.util.List;
import java.util.Optional;

public interface FormaPagoRepository extends JpaRepository <FormaPagoEntity, Long> {
    List<FormaPagoEntity> findByStatus(Boolean status);
    Optional<FormaPagoEntity> findByCodigo(String codigo);
}
