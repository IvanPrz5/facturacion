package com.ceag.facturacion.Controller.Catalogos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceag.facturacion.Entity.Catalogos.PatenteAduanalEntity;
import com.ceag.facturacion.Service.Catalogos.PatenteAduanalService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/PatenteAduanal")
public class PatenteAduanalController {
    @Autowired
    PatenteAduanalService patenteAduanalService;

    /* @GetMapping("/get")
    public List<BasicDto> getRegisters() {
        return patenteAduanalService.getRegisters();
    } */

    @PostMapping("/add")
    public ResponseEntity<PatenteAduanalEntity> addRegister(@RequestBody PatenteAduanalEntity patenteAduanal) {
        return patenteAduanalService.addRegister(patenteAduanal);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<PatenteAduanalEntity> editRegister(@PathVariable("id") Long id, @RequestBody PatenteAduanalEntity patenteAduanal) {
        return patenteAduanalService.editRegister(id, patenteAduanal);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<PatenteAduanalEntity> editStatus(@PathVariable("id") Long id, @RequestBody PatenteAduanalEntity patenteAduanal) {
        return patenteAduanalService.editStatus(id, patenteAduanal);
    }
}
