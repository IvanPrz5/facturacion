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
import com.ceag.facturacion.Entity.Catalogos.FormaPagoEntity;
import com.ceag.facturacion.Service.Catalogos.FormaPagoService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/FormaPago")
public class FormaPagoController {
    @Autowired
    FormaPagoService formaPagoService;
    
    @GetMapping("/get")
    public List<BasicDto> getRegisters() {
        return formaPagoService.getRegisters();
    }

    @PostMapping("/add")
    public ResponseEntity<FormaPagoEntity> addRegister(@RequestBody FormaPagoEntity formaPago) {
        return formaPagoService.addRegister(formaPago);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<FormaPagoEntity> editRegister(@PathVariable("id") Long id, @RequestBody FormaPagoEntity formaPago) {
        return formaPagoService.editRegister(id, formaPago);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<FormaPagoEntity> editStatus(@PathVariable("id") Long id, @RequestBody FormaPagoEntity formaPago) {
        return formaPagoService.editStatus(id, formaPago);
    }
}
