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

import com.ceag.facturacion.Dto.Catalogos.ClaveUnidadDto;
import com.ceag.facturacion.Entity.Catalogos.ClaveUnidadEntity;
import com.ceag.facturacion.Service.Catalogos.ClaveUnidadService;;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/ClaveUnidad")
public class ClaveUnidadController {
    @Autowired
    ClaveUnidadService claveUnidadService;

    @GetMapping("/byId/{codigo}")
    public List<ClaveUnidadDto> getByCodigoOrDescripcion(@PathVariable("codigo") String id) {
        return claveUnidadService.getByCodigoOrDescripcion(id);
    }

    @PostMapping("/add")
    public ResponseEntity<ClaveUnidadEntity> addRegister(@RequestBody ClaveUnidadEntity claveUnidad) {
        return claveUnidadService.addRegister(claveUnidad);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<ClaveUnidadEntity> editRegister(@PathVariable("id") Long id, @RequestBody ClaveUnidadEntity claveUnidad) {
        return claveUnidadService.editRegister(id, claveUnidad);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<ClaveUnidadEntity> editStatus(@PathVariable("id") Long id, @RequestBody ClaveUnidadEntity claveUnidad) {
        return claveUnidadService.editStatus(id, claveUnidad);
    }
}
