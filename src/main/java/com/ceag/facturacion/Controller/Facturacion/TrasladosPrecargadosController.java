package com.ceag.facturacion.Controller.Facturacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceag.facturacion.Entity.Facturacion.TrasladosPrecargadosEntity;
import com.ceag.facturacion.Service.Facturacion.TrasladosPrecargadosService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/TrasladosPrecargados")
public class TrasladosPrecargadosController {
    
    @Autowired
    TrasladosPrecargadosService trasladosPrecargadosService;

    @GetMapping("/all")
    public ResponseEntity<List<TrasladosPrecargadosEntity>> getConceptosPrecargados() throws Exception{
        return trasladosPrecargadosService.getTrasladosPrecargados();
    }
}
