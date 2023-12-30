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
import com.ceag.facturacion.Entity.Catalogos.ObjetoImpEntity;
import com.ceag.facturacion.Service.Catalogos.ObjetoImpService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/ObjetoImp")
public class ObjetoImpController {
    @Autowired
    ObjetoImpService objetoImpService;

    @GetMapping("/get")
    public List<BasicDto> getRegisters() {
        return objetoImpService.getRegisters();
    }

    @PostMapping("/add")
    public ResponseEntity<ObjetoImpEntity> addRegister(@RequestBody ObjetoImpEntity objetoImp) {
        return objetoImpService.addRegister(objetoImp);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<ObjetoImpEntity> editRegister(@PathVariable("id") Long id, @RequestBody ObjetoImpEntity objetoImp) {
        return objetoImpService.editRegister(id, objetoImp);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<ObjetoImpEntity> editStatus(@PathVariable("id") Long id, @RequestBody ObjetoImpEntity objetoImp) {
        return objetoImpService.editStatus(id, objetoImp);
    }
}
