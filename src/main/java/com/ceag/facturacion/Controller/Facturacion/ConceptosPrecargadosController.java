package com.ceag.facturacion.Controller.Facturacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceag.facturacion.Dto.Facturacion.ConceptoPrecargadoDto;
import com.ceag.facturacion.Entity.Facturacion.ConceptosPrecargadosEntity;
import com.ceag.facturacion.Service.Facturacion.ConceptosPrecargadosService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/ConceptosPrecargados")
public class ConceptosPrecargadosController {
    
    @Autowired
    ConceptosPrecargadosService conceptosPrecargadosService;

    @GetMapping("/all")
    public List<ConceptoPrecargadoDto> getConceptosPrecar() throws Exception{
        return conceptosPrecargadosService.getConceptosPre();
    }

    /* @PostMapping("/add")
    public ResponseEntity<ConceptosPrecargadosEntity> addRegister(@RequestBody ConceptosPrecargadosEntity){
        return 
    } */
}
