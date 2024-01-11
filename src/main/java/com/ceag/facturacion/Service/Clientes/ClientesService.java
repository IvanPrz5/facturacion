package com.ceag.facturacion.Service.Clientes;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Clientes.ClientesDto;
import com.ceag.facturacion.Entity.Clientes.ClientesEntity;
import com.ceag.facturacion.Repository.Clientes.ClientesRepository;

@Service
public class ClientesService {

    @Autowired
    ClientesRepository clientesRepository;

    public ClientesDto getCliente(String rfc) {
        try {
            Optional<ClientesEntity> clientes = clientesRepository.findByRfc(rfc);

            if (!clientes.isPresent()) {
                clientes = clientesRepository.findByNombreContaining(rfc);
            }

            ClientesDto clientesDto = null;
            if (clientes.isPresent()) {
                clientesDto = new ClientesDto();
                clientesDto.setNombre(clientes.get().getNombre());
                clientesDto.setRfc(clientes.get().getRfc());
                clientesDto.setDomicilioFiscal(clientes.get().getDomicilioFiscal());
                clientesDto.setRegimenFiscal(clientes.get().getRegimenFiscal().getCodigo() + ".- "
                        + clientes.get().getRegimenFiscal().getDescripcion());
                clientesDto.setUsoCfdi(clientes.get().getUsoCfdi().getCodigo() + ".- "
                        + clientes.get().getUsoCfdi().getDescripcion());
            }

            return clientesDto;
        } catch (Exception e) {
            throw new IllegalArgumentException("No se obtuvo el receptor " + e.getMessage());
        }
    }

    public ResponseEntity<ClientesEntity> addCliente(ClientesEntity cliente) {
        try {
            clientesRepository.save(cliente);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el cliente" + e.getMessage());
        }
    }
}
