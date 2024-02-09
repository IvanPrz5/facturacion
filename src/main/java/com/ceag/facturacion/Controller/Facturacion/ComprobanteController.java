package com.ceag.facturacion.Controller.Facturacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceag.facturacion.Entity.Empresas.EmpresasEntity;
import com.ceag.facturacion.Entity.Facturacion.ComprobanteEntity;
import com.ceag.facturacion.Repository.Facturacion.ComprobanteRepository;
import com.ceag.facturacion.Service.Facturacion.ComprobanteService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/Comprobante")
public class ComprobanteController {
    
    @Autowired
    ComprobanteService comprobanteService;

    @Autowired
    ComprobanteRepository comprobanteRepository;

    @GetMapping("/{tipo}/{idEmpresa}/pageable")
    public Page<ComprobanteEntity> paginacion(@PathVariable("tipo") Boolean tipo, @PathVariable("idEmpresa") EmpresasEntity idEmpresa, Pageable pageable) throws Exception{
        return comprobanteService.paginacionFacturas(tipo, idEmpresa, pageable);
    }
}
