package com.ceag.facturacion.Controller.Clientes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.ceag.facturacion.Dto.Clientes.ClientesDto;
import com.ceag.facturacion.Entity.Clientes.ClientesEntity;
import com.ceag.facturacion.Service.Clientes.ClientesService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/Clientes")
public class ClientesController {

    @Autowired
    ClientesService clientesService;
    
    @GetMapping("/byEmpresa/{idEmpresa}")
    public ResponseEntity<List<ClientesDto>> getClienteByEmpresa(@PathVariable("idEmpresa") Long id){
        return clientesService.getClientesByIdEmpresa(id); 
    }

    @GetMapping("/sinEmpresa/{rfc}")
    public ResponseEntity<List<ClientesDto>> getCliente(@PathVariable("rfc") String rfc){
        return clientesService.getClienteByRfc(rfc); 
    }

    @GetMapping("/byNombreOrRfc/{idEmpresa}/{rfc}")
    public ResponseEntity<List<ClientesDto>> getCliente(@PathVariable("idEmpresa") Long id, @PathVariable("rfc") String rfc){
        return clientesService.getCliente(id, rfc); 
    }

    @PostMapping("/add/{idEmpresa}")
    public ResponseEntity<ClientesEntity> addCliente(@PathVariable("idEmpresa") Long idEmpresa, @RequestBody ClientesEntity clientes){
        return clientesService.addCliente(idEmpresa, clientes);
    }

    @PostMapping("/edit/{idCliente}")
    public ResponseEntity<ClientesEntity> editCliente(@PathVariable("idCliente") Long idCliente, @RequestBody ClientesEntity clientes){
        return clientesService.addCliente(idCliente, clientes);
    }
}
