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
import com.ceag.facturacion.Entity.Catalogos.ExportacionEntity;
import com.ceag.facturacion.Service.Catalogos.ExportacionService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/Exportacion")
public class ExportacionController {
    @Autowired
    ExportacionService exportacionService;

    @GetMapping("/get")
    public List<BasicDto> getRegisters() {
        return exportacionService.getRegisters();
    }
    
    @PostMapping("/add")
    public ResponseEntity<ExportacionEntity> addRegister(@RequestBody ExportacionEntity exportacion) {
        return exportacionService.addRegister(exportacion);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<ExportacionEntity> editRegister(@PathVariable("id") Long id, @RequestBody ExportacionEntity exportacion) {
        return exportacionService.editRegister(id, exportacion);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<ExportacionEntity> editStatus(@PathVariable("id") Long id, @RequestBody ExportacionEntity exportacion) {
        return exportacionService.editStatus(id, exportacion);
    }
}
