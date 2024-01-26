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
    
    @GetMapping("/byNombreOrRfc/{rfc}")
    public ResponseEntity<List<ClientesDto>> getCliente(@PathVariable("rfc") String rfc){
        return clientesService.getCliente(rfc); 
    }

    @PostMapping("/add")
    public ResponseEntity<ClientesEntity> addCliente(@RequestBody ClientesEntity clientes){
        return clientesService.addCliente(clientes);
    }
}
