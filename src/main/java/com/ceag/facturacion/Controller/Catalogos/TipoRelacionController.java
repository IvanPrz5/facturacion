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
import com.ceag.facturacion.Entity.Catalogos.TipoRelacionEntity;
import com.ceag.facturacion.Service.Catalogos.TipoRelacionService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("auth/TipoRelacion")
public class TipoRelacionController {
    @Autowired
    TipoRelacionService tipoRelacionService;

    @GetMapping("/get")
    public List<BasicDto> getRegisters() {
        return tipoRelacionService.getRegisters();
    }

    @PostMapping("/add")
    public ResponseEntity<TipoRelacionEntity> addRegister(@RequestBody TipoRelacionEntity tipoRelacion) {
        return tipoRelacionService.addRegister(tipoRelacion);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<TipoRelacionEntity> editRegister(@PathVariable("id") Long id, @RequestBody TipoRelacionEntity tipoRelacion) {
        return tipoRelacionService.editRegister(id, tipoRelacion);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<TipoRelacionEntity> editStatus(@PathVariable("id") Long id, @RequestBody TipoRelacionEntity tipoRelacion) {
        return tipoRelacionService.editStatus(id, tipoRelacion);
    }
}
