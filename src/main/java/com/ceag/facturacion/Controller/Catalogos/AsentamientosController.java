package com.ceag.facturacion.Controller.Catalogos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Entity.Catalogos.AsentamientosEntity;
import com.ceag.facturacion.Service.Catalogos.AsentamientosService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/Asentamientos")
public class AsentamientosController {
    
    @Autowired
    AsentamientosService asentamientosService;

    @GetMapping("/get")
    public List<BasicDto> getRegisters() {
        return asentamientosService.getRegisters();
    }

    @PostMapping("/add")
    public ResponseEntity<AsentamientosEntity> addRegister(@RequestBody AsentamientosEntity asentamientos) {
        return asentamientosService.addRegister(asentamientos);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<AsentamientosEntity> editRegister(@PathVariable("id") Long id, @RequestBody AsentamientosEntity asentamientos) {
        return asentamientosService.editRegister(id, asentamientos);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<AsentamientosEntity> editStatus(@PathVariable("id") Long id, @RequestBody AsentamientosEntity asentamientos) {
        return asentamientosService.editStatus(id, asentamientos);
    }
}
