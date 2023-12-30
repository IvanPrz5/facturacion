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
import com.ceag.facturacion.Entity.Catalogos.MetodoPagoEntity;
import com.ceag.facturacion.Service.Catalogos.MetodoPagoService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/MetodoPago")
public class MetodoPagoController {
    @Autowired
    MetodoPagoService metodoPagoService;

    @GetMapping("/get")
    public List<BasicDto> getRegisters() {
        return metodoPagoService.getRegisters();
    }
    
    @PostMapping("/add")
    public ResponseEntity<MetodoPagoEntity> addRegister(@RequestBody MetodoPagoEntity metodoPago) {
        return metodoPagoService.addRegister(metodoPago);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<MetodoPagoEntity> editRegister(@PathVariable("id") Long id, @RequestBody MetodoPagoEntity metodoPago) {
        return metodoPagoService.editRegister(id, metodoPago);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<MetodoPagoEntity> editStatus(@PathVariable("id") Long id, @RequestBody MetodoPagoEntity metodoPago) {
        return metodoPagoService.editStatus(id, metodoPago);
    }
}
