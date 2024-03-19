package com.ceag.facturacion.Repository.Usuarios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ceag.facturacion.Entity.Usuarios.UsuariosEntity;

public interface UsuariosRepository extends JpaRepository <UsuariosEntity, Long> {
    Optional<UsuariosEntity> findOneByEmail(String email);
    List<UsuariosEntity> findByIdAndStatus(Long id, Boolean status);
    List<UsuariosEntity> findByStatusOrderById(Boolean status);

    @Modifying
    @Query(value = "INSERT INTO user_roles (role_id, user_id) VALUES (?1, ?2)", nativeQuery = true)
    List<UsuariosEntity> insertIntoUserRoles(Long idRole, Long idUser);

    @Modifying
    @Query(value = "INSERT INTO usuario_empresa (user_id, empresa_id) VALUES (?1, ?2);", nativeQuery = true)
    List<UsuariosEntity> insertIntoUsuarioEmpresa(Long idUser, Long idEmpresa);

    @Modifying
    @Query(value = "DELETE FROM usuario_empresa WHERE user_id = ?1 AND empresa_id = ?2", nativeQuery = true)
    List<UsuariosEntity> deleteUsuarioEmpresa(Long idUser, Long idEmpresa);
}
