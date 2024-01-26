package com.ceag.facturacion.Service.Clientes;

import java.util.ArrayList;
import java.util.List;

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

    public ResponseEntity<List<ClientesDto>> getCliente(String rfc) {
        try {
            List<ClientesEntity> clientes = clientesRepository.findByRfcContaining(rfc);
            if (clientes.isEmpty()) {
                clientes = clientesRepository.findByNombreContaining(rfc);
            }
            List<ClientesDto> lista = new ArrayList<>();
            for(int i=0; i<clientes.size(); i++){
                ClientesDto clientesDto = new ClientesDto();
                clientesDto.setNombre(clientes.get(i).getNombre());
                clientesDto.setRfc(clientes.get(i).getRfc());
                clientesDto.setDomicilioFiscal(clientes.get(i).getDomicilioFiscal());
                clientesDto.setRegimenFiscal(clientes.get(i).getRegimenFiscal().getCodigo() + ".- " + clientes.get(i).getRegimenFiscal().getDescripcion());
                clientesDto.setUsoCfdi(clientes.get(i).getUsoCfdi().getCodigo() + ".- " + clientes.get(i).getUsoCfdi().getDescripcion());
                lista.add(clientesDto);
            }

            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se obtuvieron los datos" + e.getMessage());
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
