package com.ceag.facturacion.Repository.Usuarios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.ceag.facturacion.Entity.Usuarios.RolesEntity;
import com.ceag.facturacion.Entity.Usuarios.UsuariosEntity;

public interface RolesRepository extends JpaRepository<RolesEntity, Long> {
    List<RolesEntity> findByStatus(Boolean status);

    @Query(value = "select * from roles ro join user_roles ur ON ur.role_id = ro.id where ur.user_id = ?1", nativeQuery = true)
    List<RolesEntity> queryJoin(Long idUser);

    @Modifying
    @Query(value = "DELETE FROM user_roles WHERE role_id = ?1 AND user_id = ?2", nativeQuery = true)
    List<UsuariosEntity> deleteUserRole(Long idRole, Long idUser);
}
