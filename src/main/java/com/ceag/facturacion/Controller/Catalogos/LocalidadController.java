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
import com.ceag.facturacion.Entity.Catalogos.LocalidadEntity;
import com.ceag.facturacion.Service.Catalogos.LocalidadService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/Localidad")
public class LocalidadController {
    @Autowired
    LocalidadService localidadService;

    /* @GetMapping("/get")
    public List<BasicDto> getRegisters() {
        return localidadService.getRegisters();
    } */

    @PostMapping("/add")
    public ResponseEntity<LocalidadEntity> addRegister(@RequestBody LocalidadEntity impuesto) {
        return localidadService.addRegister(impuesto);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<LocalidadEntity> editRegister(@PathVariable("id") Long id, @RequestBody LocalidadEntity impuesto) {
        return localidadService.editRegister(id, impuesto);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<LocalidadEntity> editStatus(@PathVariable("id") Long id, @RequestBody LocalidadEntity impuesto) {
        return localidadService.editStatus(id, impuesto);
    }
}
