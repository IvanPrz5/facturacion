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
import com.ceag.facturacion.Entity.Catalogos.TipoComprobanteEntity;
import com.ceag.facturacion.Service.Catalogos.TipoComprobanteService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/TipoComprobante")
public class TipoComprobanteController {
    @Autowired
    TipoComprobanteService tipoComprobanteService;

    @GetMapping("/get")
    public List<BasicDto> getRegisters() {
        return tipoComprobanteService.getRegisters();
    }

    @PostMapping("/add")
    public ResponseEntity<TipoComprobanteEntity> addRegister(@RequestBody TipoComprobanteEntity tipoComprobante) {
        return tipoComprobanteService.addRegister(tipoComprobante);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<TipoComprobanteEntity> editRegister(@PathVariable("id") Long id, @RequestBody TipoComprobanteEntity tipoComprobante) {
        return tipoComprobanteService.editRegister(id, tipoComprobante);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<TipoComprobanteEntity> editStatus(@PathVariable("id") Long id, @RequestBody TipoComprobanteEntity tipoComprobante) {
        return tipoComprobanteService.editStatus(id, tipoComprobante);
    }
}
