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
import com.ceag.facturacion.Entity.Catalogos.AduanaEntity;
import com.ceag.facturacion.Service.Catalogos.AduanaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;


@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/Aduana")
public class AduanaController {
    @Autowired
    AduanaService aduanaService;

    @GetMapping("/get")
    public List<BasicDto> getRegisters() {
        return aduanaService.getRegisters();
    }

    @PostMapping("/add")
    public ResponseEntity<AduanaEntity> addRegister(@RequestBody AduanaEntity aduana) {
        return aduanaService.addRegister(aduana);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<AduanaEntity> editRegister(@PathVariable("id") Long id, @RequestBody AduanaEntity aduana) {
        return aduanaService.editRegister(id, aduana);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<AduanaEntity> editStatus(@PathVariable("id") Long id, @RequestBody AduanaEntity aduana) {
        return aduanaService.editStatus(id, aduana);
    }
}