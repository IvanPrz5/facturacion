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
import com.ceag.facturacion.Entity.Catalogos.PeriodicidadEntity;
import com.ceag.facturacion.Service.Catalogos.PeriodicidadService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/Periodicidad")
public class PeriodicidadController {
    @Autowired
    PeriodicidadService periodicidadService;

    @GetMapping("/get")
    public List<BasicDto> getRegisters() {
        return periodicidadService.getRegisters();
    }

    @PostMapping("/add")
    public ResponseEntity<PeriodicidadEntity> addRegister(@RequestBody PeriodicidadEntity periodicidad) {
        return periodicidadService.addRegister(periodicidad);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<PeriodicidadEntity> editRegister(@PathVariable("id") Long id, @RequestBody PeriodicidadEntity periodicidad) {
        return periodicidadService.editRegister(id, periodicidad);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<PeriodicidadEntity> editStatus(@PathVariable("id") Long id, @RequestBody PeriodicidadEntity periodicidad) {
        return periodicidadService.editStatus(id, periodicidad);
    }
}
