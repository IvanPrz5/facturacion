package com.ceag.facturacion.Controller.Facturacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceag.facturacion.Service.Facturacion.Xml.CrearXml;
import com.ceag.facturacion.Utils.DatosFactura.DatosFactura;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/Facturacion")
public class FacturacionController {
    @Autowired
    CrearXml crearXml;

    @PostMapping("/crearXml")
    public String formarXml (@RequestBody DatosFactura datosFactura) {
        return crearXml.formarXml(datosFactura);
    }
    
}
