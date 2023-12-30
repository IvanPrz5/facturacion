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
import com.ceag.facturacion.Entity.Catalogos.PaisEntity;
import com.ceag.facturacion.Service.Catalogos.PaisService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/Pais")
public class PaisController {
    @Autowired
    PaisService paisService;

    @GetMapping("/get")
    public List<BasicDto> getRegisters() {
        return paisService.getRegisters();
    }

    @PostMapping("/add")
    public ResponseEntity<PaisEntity> addRegister(@RequestBody PaisEntity pais) {
        return paisService.addRegister(pais);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<PaisEntity> editRegister(@PathVariable("id") Long id, @RequestBody PaisEntity pais) {
        return paisService.editRegister(id, pais);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<PaisEntity> editStatus(@PathVariable("id") Long id, @RequestBody PaisEntity pais) {
        return paisService.editStatus(id, pais);
    }
}
