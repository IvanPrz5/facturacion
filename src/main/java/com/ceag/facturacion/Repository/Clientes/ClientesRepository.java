package com.ceag.facturacion.Repository.Clientes;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ceag.facturacion.Entity.Clientes.ClientesEntity;

public interface ClientesRepository extends JpaRepository<ClientesEntity, Long>{
    List<ClientesEntity> findByRfcContaining(String rfc);
    List<ClientesEntity> findByNombreContaining(String nombre);
}
