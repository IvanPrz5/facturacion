package com.ceag.facturacion.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Usuarios.UsuariosEntity;

public interface UsuariosRepository extends JpaRepository <UsuariosEntity, Long> {
    Optional<UsuariosEntity> findOneByEmail(String email);
    List<UsuariosEntity> findByIdAndStatus(Long id, Boolean status);
}
