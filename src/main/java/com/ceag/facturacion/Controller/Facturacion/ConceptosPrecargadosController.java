package com.ceag.facturacion.Controller.Facturacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceag.facturacion.Dto.Facturacion.ConceptoPrecargadoDto;
import com.ceag.facturacion.Entity.Empresas.EmpresasEntity;
import com.ceag.facturacion.Service.Facturacion.ConceptosPrecargadosService;
import com.ceag.facturacion.Utils.DatosFactura.DatosPrecargados;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/ConceptosPrecargados")
public class ConceptosPrecargadosController {
    
    @Autowired
    ConceptosPrecargadosService conceptosPrecargadosService;

    @GetMapping("/all/{idEmpresa}")
    public List<ConceptoPrecargadoDto> getConceptosPrecar(@PathVariable("idEmpresa") EmpresasEntity idEmpresa) throws Exception{
        return conceptosPrecargadosService.getConceptosPre(idEmpresa);
    }

    @PostMapping("/add")
    public ResponseEntity<Long> addRegister(@RequestBody DatosPrecargados datosFactura){
        return conceptosPrecargadosService.addRegister(datosFactura);
    }
}
