package com.ceag.facturacion.Controller.Empresas;

import java.util.List;

import com.ceag.facturacion.Dto.Empresas.EmpresasDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceag.facturacion.Entity.Empresas.EmpresasEntity;
import com.ceag.facturacion.Service.Empresas.EmpresasService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/Empresas")
public class EmpresasController {
    @Autowired
    EmpresasService empresasService;

    @GetMapping("/getAll")
    public List<EmpresasDto> getAll() {
        return empresasService.getAll();
    }
}