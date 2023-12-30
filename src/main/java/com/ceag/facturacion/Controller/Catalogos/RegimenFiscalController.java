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
import com.ceag.facturacion.Entity.Catalogos.RegimenFiscalEntity;
import com.ceag.facturacion.Service.Catalogos.RegimenFiscalService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/RegimenFiscal")
public class RegimenFiscalController {
    @Autowired
    RegimenFiscalService regimenFiscalService;

    @GetMapping("/get")
    public List<BasicDto> getRegisters() {
        return regimenFiscalService.getRegisters();
    }

    @PostMapping("/add")
    public ResponseEntity<RegimenFiscalEntity> addRegister(@RequestBody RegimenFiscalEntity regimenFiscal) {
        return regimenFiscalService.addRegister(regimenFiscal);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<RegimenFiscalEntity> editRegister(@PathVariable("id") Long id, @RequestBody RegimenFiscalEntity regimenFiscal) {
        return regimenFiscalService.editRegister(id, regimenFiscal);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<RegimenFiscalEntity> editStatus(@PathVariable("id") Long id, @RequestBody RegimenFiscalEntity regimenFiscal) {
        return regimenFiscalService.editStatus(id, regimenFiscal);
    }
}
