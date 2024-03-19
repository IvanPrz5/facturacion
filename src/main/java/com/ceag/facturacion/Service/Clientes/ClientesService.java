package com.ceag.facturacion.Service.Clientes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Clientes.ClientesDto;
import com.ceag.facturacion.Entity.Clientes.ClientesEntity;
import com.ceag.facturacion.Entity.Empresas.EmpresasEntity;
import com.ceag.facturacion.Repository.Clientes.ClientesRepository;
import com.ceag.facturacion.Repository.Empresas.EmpresasRepository;

@Service
public class ClientesService {

    @Autowired
    ClientesRepository clientesRepository;

    @Autowired
    EmpresasRepository empresasRepository;

    public ResponseEntity<List<ClientesDto>> getClientesByIdEmpresa(Long id) {
        try {
            // List<EmpresasEntity> empresas = empresasRepository.findByClientes();
            List<EmpresasEntity> clientes = empresasRepository.findByIdAndStatus(id, true);
            List<ClientesDto> lista = new ArrayList<>();
            for(int i=0; i<clientes.size(); i++){
                for(int j=0; j<clientes.get(i).getClientes().size(); j++){
                    ClientesDto clientesDto = new ClientesDto();
                    clientesDto.setId(clientes.get(i).getClientes().get(j).getId());
                    clientesDto.setNombre(clientes.get(i).getClientes().get(j).getNombre());
                    clientesDto.setRfc(clientes.get(i).getClientes().get(j).getRfc());
                    clientesDto.setDomicilioFiscal(clientes.get(i).getClientes().get(j).getDomicilioFiscal());
                    clientesDto.setIdRegimenFiscal(clientes.get(i).getClientes().get(j).getRegimenFiscal().getId());
                    // clientesDto.setIdUso(clientes.get(i).getClientes().get(j).getUsoCfdi().getId());
                    clientesDto.setRegimenFiscal(clientes.get(i).getClientes().get(j).getRegimenFiscal().getCodigo() + ".- " + clientes.get(i).getClientes().get(j).getRegimenFiscal().getDescripcion());
                    // clientesDto.setUsoCfdi(clientes.get(i).getClientes().get(j).getUsoCfdi().getCodigo() + ".- " + clientes.get(i).getClientes().get(j).getUsoCfdi().getDescripcion());
                    lista.add(clientesDto);
                }
            }

            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se obtuvieron los datos" + e.getMessage());
        }
    }

    public ResponseEntity<List<ClientesDto>> getCliente(Long id, String rfc) {
        try {
            // List<EmpresasEntity> empresas = empresasRepository.findByClientes();
            List<EmpresasEntity> clientes = empresasRepository.findByClientes_RfcContaining(rfc);
            if (clientes.isEmpty()) {
                clientes = empresasRepository.findByIdAndClientes_NombreContaining(id, rfc);
            }
            List<ClientesDto> lista = new ArrayList<>();
            for(int i=0; i<clientes.size(); i++){
                for(int j=0; j<clientes.get(i).getClientes().size(); j++){
                    ClientesDto clientesDto = new ClientesDto();
                    clientesDto.setId(clientes.get(i).getClientes().get(j).getId());
                    clientesDto.setNombre(clientes.get(i).getClientes().get(j).getNombre());
                    clientesDto.setRfc(clientes.get(i).getClientes().get(j).getRfc());
                    clientesDto.setDomicilioFiscal(clientes.get(i).getClientes().get(j).getDomicilioFiscal());
                    clientesDto.setIdRegimenFiscal(clientes.get(i).getClientes().get(j).getRegimenFiscal().getId());
                    // clientesDto.setIdUso(clientes.get(i).getClientes().get(j).getUsoCfdi().getId());
                    clientesDto.setRegimenFiscal(clientes.get(i).getClientes().get(j).getRegimenFiscal().getCodigo() + ".- " + clientes.get(i).getClientes().get(j).getRegimenFiscal().getDescripcion());
                    // clientesDto.setUsoCfdi(clientes.get(i).getClientes().get(j).getUsoCfdi().getCodigo() + ".- " + clientes.get(i).getClientes().get(j).getUsoCfdi().getDescripcion());
                    lista.add(clientesDto);
                }
            }

            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se obtuvieron los datos" + e.getMessage());
        }
    }

    public ResponseEntity<List<ClientesDto>> getClienteByRfc(String rfc) {
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
                // clientesDto.setUsoCfdi(clientes.get(i).getUsoCfdi().getCodigo() + ".- " + clientes.get(i).getUsoCfdi().getDescripcion());
                lista.add(clientesDto);
            }

            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se obtuvieron los datos" + e.getMessage());
        }
    }

    public ResponseEntity<ClientesEntity> addCliente(Long idEmpresa, ClientesEntity cliente) {
        try {
            ClientesEntity clienteId = clientesRepository.save(cliente);
            try {
                empresasRepository.insertIntoEmpresaCliente(idEmpresa, clienteId.getId());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el cliente" + e.getMessage());
        }
    }

    public ResponseEntity<ClientesEntity> editRegister(Long id, ClientesEntity clientes){
        Optional<ClientesEntity> clientesId = clientesRepository.findById(id);
        
        if(clientesId.isPresent()){
            clientesRepository.save(clientes);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + clientes);
        }
    }
}
