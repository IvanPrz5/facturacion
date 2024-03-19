package com.ceag.facturacion.Repository.Empresas;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ceag.facturacion.Entity.Empresas.EmpresasEntity;
import com.ceag.facturacion.Entity.Usuarios.UsuariosEntity;

public interface EmpresasRepository extends JpaRepository <EmpresasEntity, Long> {
    List<EmpresasEntity> findByIdAndStatus(Long id, Boolean status);
    List<EmpresasEntity> findByStatus(Boolean status);

    @Modifying
    @Query(value = "INSERT INTO empresa_cliente (empresa_id, cliente_id) VALUES (?1, ?2);", nativeQuery = true)
    List<UsuariosEntity> insertIntoEmpresaCliente(Long idEmpresa, Long idCliente);

    List<EmpresasEntity> findByClientes_RfcContaining(String rfc);
    List<EmpresasEntity> findByIdAndClientes_NombreContaining(Long id, String nombre);

    @Query(value = "select * from empresas em join usuario_empresa ue ON ue.empresa_id = em.id where ue.user_id = ?1", nativeQuery = true)
    List<EmpresasEntity> queryJoin(Long idUser);
}