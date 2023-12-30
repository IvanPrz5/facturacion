package com.ceag.facturacion.Repository.Catalogos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Catalogos.NumPedAduanaEntity;
import java.util.List;

public interface NumPedAduanaRepository extends JpaRepository <NumPedAduanaEntity, Long> {
    List<NumPedAduanaEntity> findByStatus(Boolean status);
}
