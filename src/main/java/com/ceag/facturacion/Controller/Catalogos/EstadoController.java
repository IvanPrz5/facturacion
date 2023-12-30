package com.ceag.facturacion.Controller.Catalogos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Entity.Catalogos.EstadoEntity;
import com.ceag.facturacion.Service.Catalogos.EstadoService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/Estado")
public class EstadoController {
    @Autowired
    EstadoService estadoService;

    @GetMapping("/get/{id}")
    public List<BasicDto> getRegisters(@PathVariable("id") Long id) {
        return estadoService.getRegisters(id);
    }

    @PostMapping("/add")
    public ResponseEntity<EstadoEntity> addRegister(@RequestBody EstadoEntity estado) {
        return estadoService.addRegister(estado);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<EstadoEntity> editRegister(@PathVariable("id") Long id, @RequestBody EstadoEntity estado) {
        return estadoService.editRegister(id, estado);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<EstadoEntity> editStatus(@PathVariable("id") Long id, @RequestBody EstadoEntity estado) {
        return estadoService.editStatus(id, estado);
    }
}
