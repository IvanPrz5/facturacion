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
import com.ceag.facturacion.Entity.Catalogos.UsoCFDIEntity;
import com.ceag.facturacion.Service.Catalogos.UsoCFDIService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/UsoCFDI")
public class UsoCFDIController {
    @Autowired
    UsoCFDIService usoCfdiService;

    @GetMapping("/get")
    public List<BasicDto> getRegisters() {
        return usoCfdiService.getRegisters();
    }

    @PostMapping("/add")
    public ResponseEntity<UsoCFDIEntity> addRegister(@RequestBody UsoCFDIEntity usoCfdi) {
        return usoCfdiService.addRegister(usoCfdi);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<UsoCFDIEntity> editRegister(@PathVariable("id") Long id, @RequestBody UsoCFDIEntity usoCfdi) {
        return usoCfdiService.editRegister(id, usoCfdi);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<UsoCFDIEntity> editStatus(@PathVariable("id") Long id, @RequestBody UsoCFDIEntity usoCfdi) {
        return usoCfdiService.editStatus(id, usoCfdi);
    }
}
