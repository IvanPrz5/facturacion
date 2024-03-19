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

import com.ceag.facturacion.Entity.Catalogos.MunicipioEntity;
import com.ceag.facturacion.Service.Catalogos.MunicipioService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/Municipio")
public class MunicipioController {
    @Autowired
    MunicipioService municipioService;

    /* @GetMapping("/get")
    public List<BasicDto> getRegisters() {
        return municipioService.getRegisters();
    } */
    
    @PostMapping("/add")
    public ResponseEntity<MunicipioEntity> addRegister(@RequestBody MunicipioEntity municipio) {
        return municipioService.addRegister(municipio);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<MunicipioEntity> editRegister(@PathVariable("id") Long id, @RequestBody MunicipioEntity municipio) {
        return municipioService.editRegister(id, municipio);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<MunicipioEntity> editStatus(@PathVariable("id") Long id, @RequestBody MunicipioEntity municipio) {
        return municipioService.editStatus(id, municipio);
    }
}
