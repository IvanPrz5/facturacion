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
import com.ceag.facturacion.Entity.Catalogos.MesesEntity;
import com.ceag.facturacion.Service.Catalogos.MesesService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/Meses")
public class MesesController {
    @Autowired
    MesesService mesesService;

    @GetMapping("/get")
    public List<BasicDto> getRegisters() {
        return mesesService.getRegisters();
    }

    @PostMapping("/add")
    public ResponseEntity<MesesEntity> addRegister(@RequestBody MesesEntity meses) {
        return mesesService.addRegister(meses);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<MesesEntity> editRegister(@PathVariable("id") Long id, @RequestBody MesesEntity meses) {
        return mesesService.editRegister(id, meses);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<MesesEntity> editStatus(@PathVariable("id") Long id, @RequestBody MesesEntity meses) {
        return mesesService.editStatus(id, meses);
    }
}
