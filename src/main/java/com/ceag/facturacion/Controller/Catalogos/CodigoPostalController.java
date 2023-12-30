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
import com.ceag.facturacion.Entity.Catalogos.CodigoPostalEntity;
import com.ceag.facturacion.Service.Catalogos.CodigoPostalService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/CodigoPostal")
public class CodigoPostalController {
    @Autowired
    CodigoPostalService codigoPostalService;

    @GetMapping("/byId/{id}")
    public ResponseEntity<List<BasicDto>> getCodigoPostal(@PathVariable("id") Long id) {
        return codigoPostalService.getCodigoPostal(id);
    }

    @PostMapping("/add")
    public ResponseEntity<CodigoPostalEntity> addRegister(@RequestBody CodigoPostalEntity codigoPostal) {
        return codigoPostalService.addRegister(codigoPostal);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<CodigoPostalEntity> editRegister(@PathVariable("id") Long id, @RequestBody CodigoPostalEntity codigoPostal) {
        return codigoPostalService.editRegister(id, codigoPostal);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<CodigoPostalEntity> editStatus(@PathVariable("id") Long id, @RequestBody CodigoPostalEntity codigoPostal) {
        return codigoPostalService.editStatus(id, codigoPostal);
    }
}
