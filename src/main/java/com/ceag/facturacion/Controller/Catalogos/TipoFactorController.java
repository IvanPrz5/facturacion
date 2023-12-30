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
import com.ceag.facturacion.Entity.Catalogos.TipoFactorEntity;
import com.ceag.facturacion.Service.Catalogos.TipoFactorService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/TipoFactor")
public class TipoFactorController {
    @Autowired
    TipoFactorService tipoFactorService;

    @GetMapping("/get")
    public ResponseEntity<List<BasicDto>> getByCodigoOrDescripcion(){
        return tipoFactorService.getByStatus();
    }

    @PostMapping("/add")
    public ResponseEntity<TipoFactorEntity> addRegister(@RequestBody TipoFactorEntity tipoFactor) {
        return tipoFactorService.addRegister(tipoFactor);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<TipoFactorEntity> editRegister(@PathVariable("id") Long id, @RequestBody TipoFactorEntity tipoFactor) {
        return tipoFactorService.editRegister(id, tipoFactor);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<TipoFactorEntity> editStatus(@PathVariable("id") Long id, @RequestBody TipoFactorEntity tipoFactor) {
        return tipoFactorService.editStatus(id, tipoFactor);
    }
}
