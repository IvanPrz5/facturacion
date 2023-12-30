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
import com.ceag.facturacion.Entity.Catalogos.ImpuestoEntity;
import com.ceag.facturacion.Service.Catalogos.ImpuestoService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/Impuesto")
public class ImpuestoController {
    @Autowired
    ImpuestoService impuestoService;

    @GetMapping("/get")
    public List<BasicDto> getRegisters() {
        return impuestoService.getRegisters();
    }
    
    @PostMapping("/add")
    public ResponseEntity<ImpuestoEntity> addRegister(@RequestBody ImpuestoEntity impuesto) {
        return impuestoService.addRegister(impuesto);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<ImpuestoEntity> editRegister(@PathVariable("id") Long id, @RequestBody ImpuestoEntity impuesto) {
        return impuestoService.editRegister(id, impuesto);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<ImpuestoEntity> editStatus(@PathVariable("id") Long id, @RequestBody ImpuestoEntity impuesto) {
        return impuestoService.editStatus(id, impuesto);
    }
}
