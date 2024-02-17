package com.ceag.facturacion.Repository.Facturacion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;
import java.time.LocalDateTime;

import com.ceag.facturacion.Entity.Empresas.EmpresasEntity;
import com.ceag.facturacion.Entity.Facturacion.ComprobanteEntity;

public interface ComprobanteRepository extends JpaRepository<ComprobanteEntity, Long> {
    Optional<ComprobanteEntity> findByUuidAndStatus(String uuid, Boolean status);

    List<ComprobanteEntity> findByIsTimbrado(Boolean status);

    Page<ComprobanteEntity> findByIsTimbradoAndIsCanceladoAndIdEmpresaAndStatusOrderByFechaDesc(Boolean isTimbrado,
            Boolean isCancelado, EmpresasEntity empresa, Boolean status, Pageable pageable);

    List<ComprobanteEntity> findByUuidContainingAndIsTimbradoAndIsCanceladoAndIdEmpresaAndStatusOrderByFechaDesc(String uuid,
            Boolean isTimbrado, Boolean isCancelado, EmpresasEntity empresa, Boolean status);

    List<ComprobanteEntity> findByFechaBetweenAndIsTimbradoAndIsCanceladoAndIdEmpresaAndStatusOrderByFechaDesc(
            LocalDateTime fecha, LocalDateTime fechaDos,
            Boolean isTimbrado, Boolean isCancelado, EmpresasEntity empresa, Boolean status);

    List<ComprobanteEntity> findByTotalAndIsTimbradoAndIsCanceladoAndIdEmpresaAndStatusOrderByFechaDesc(Double total,
            Boolean isTimbrado, Boolean isCancelado, EmpresasEntity empresa, Boolean status);
}
