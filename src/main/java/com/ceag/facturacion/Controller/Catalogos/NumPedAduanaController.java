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
import com.ceag.facturacion.Entity.Catalogos.NumPedAduanaEntity;
import com.ceag.facturacion.Service.Catalogos.NumPedAduanaService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })

@RestController
@RequestMapping("/api/v1/NumPedAduana")
public class NumPedAduanaController {
    @Autowired
    NumPedAduanaService numPedAduanaService;

    /* @GetMapping("/get")
    public List<BasicDto> getRegisters() {
        return numPedAduanaService.getRegisters();
    } */

    @PostMapping("/add")
    public ResponseEntity<NumPedAduanaEntity> addRegister(@RequestBody NumPedAduanaEntity numPedAduana) {
        return numPedAduanaService.addRegister(numPedAduana);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<NumPedAduanaEntity> editRegister(@PathVariable("id") Long id, @RequestBody NumPedAduanaEntity numPedAduana) {
        return numPedAduanaService.editRegister(id, numPedAduana);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<NumPedAduanaEntity> editStatus(@PathVariable("id") Long id, @RequestBody NumPedAduanaEntity numPedAduana) {
        return numPedAduanaService.editStatus(id, numPedAduana);
    }
}
