package com.ceag.facturacion.Controller.Facturacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceag.facturacion.Dto.Facturacion.ConceptoPrecargadoDto;
import com.ceag.facturacion.Service.Facturacion.ConceptosPrecargadosService;

import org.springframework.web.bind.annotation.GetMapping;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/ConceptosPrecargados")
public class ConceptosPrecargadosController {
    
    @Autowired
    ConceptosPrecargadosService conceptosPrecargadosService;

    @GetMapping("/all")
    public List<ConceptoPrecargadoDto> getConceptosPrecargados() throws Exception{
        return conceptosPrecargadosService.getConceptosPre();
    }
}
