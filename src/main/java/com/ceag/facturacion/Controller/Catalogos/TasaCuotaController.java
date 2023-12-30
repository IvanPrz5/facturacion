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
import com.ceag.facturacion.Entity.Catalogos.TasaCuotaEntity;
import com.ceag.facturacion.Service.Catalogos.TasaCuotaService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/TasaoCuota")
public class TasaCuotaController {
    @Autowired
    TasaCuotaService tasaCuotaService;

    @GetMapping("/get")
    public ResponseEntity<List<BasicDto>> getRegisters() {
        return tasaCuotaService.getByStatus();
    }

    @PostMapping("/add")
    public ResponseEntity<TasaCuotaEntity> addRegister(@RequestBody TasaCuotaEntity tasaCuota) {
        return tasaCuotaService.addRegister(tasaCuota);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<TasaCuotaEntity> editRegister(@PathVariable("id") Long id, @RequestBody TasaCuotaEntity tasaCuota) {
        return tasaCuotaService.editRegister(id, tasaCuota);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<TasaCuotaEntity> editStatus(@PathVariable("id") Long id, @RequestBody TasaCuotaEntity tasaCuota) {
        return tasaCuotaService.editStatus(id, tasaCuota);
    }
}
