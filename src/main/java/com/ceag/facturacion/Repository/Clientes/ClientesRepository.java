package com.ceag.facturacion.Repository.Clientes;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Clientes.ClientesEntity;

public interface ClientesRepository extends JpaRepository<ClientesEntity, Long>{
    Optional<ClientesEntity> findByRfc(String rfc);
    Optional<ClientesEntity> findByNombreContaining(String nombre);
}
