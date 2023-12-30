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
import com.ceag.facturacion.Entity.Catalogos.ClaveProdServEntity;
import com.ceag.facturacion.Service.Catalogos.ClaveProdServService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("api/v1/ClaveProdServ")
public class ClaveProdServController {
    @Autowired
    ClaveProdServService claveProdServService;

    @GetMapping("/byCod/{cod}")
    public List<BasicDto> getRegisters(@PathVariable("cod") String codigo) {
        return claveProdServService.getRegistersByCodigo(codigo);
    }

    @PostMapping("/add")
    public ResponseEntity<ClaveProdServEntity> addRegister(@RequestBody ClaveProdServEntity claveProdServ) {
        return claveProdServService.addRegister(claveProdServ);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<ClaveProdServEntity> editRegister(@PathVariable("id") Long id, @RequestBody ClaveProdServEntity claveProdServ) {
        return claveProdServService.editRegister(id, claveProdServ);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<ClaveProdServEntity> editStatus(@PathVariable("id") Long id, @RequestBody ClaveProdServEntity claveProdServ) {
        return claveProdServService.editStatus(id, claveProdServ);
    }
}
