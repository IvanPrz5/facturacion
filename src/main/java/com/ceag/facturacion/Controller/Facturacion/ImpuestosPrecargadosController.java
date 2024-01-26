package com.ceag.facturacion.Controller.Facturacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceag.facturacion.Entity.Facturacion.ImpuestosPrecargadosEntity;
import com.ceag.facturacion.Service.Facturacion.ImpuestosPrecargadosService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/ImpuestosPrecargados")
public class ImpuestosPrecargadosController {
    
    @Autowired
    ImpuestosPrecargadosService trasladosPrecargadosService;

    @GetMapping("/all")
    public ResponseEntity<List<ImpuestosPrecargadosEntity>> getConceptosPrecargados() throws Exception{
        return trasladosPrecargadosService.getTrasladosPrecargados();
    }
}
