package com.ceag.facturacion.Controller.Facturacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceag.facturacion.Dto.Xml.RespuestaTimbrado;
import com.ceag.facturacion.Service.Facturacion.CrearXmlService;
import com.ceag.facturacion.Utils.DatosFactura.DatosFactura;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/Facturacion")
public class FacturacionController {
    
    @Autowired
    CrearXmlService crearXmlService;

    @PostMapping("/crearXml")
    public RespuestaTimbrado formarXml(@RequestBody DatosFactura datosFactura) throws Exception{ 
        return crearXmlService.formarAndTimbrarXml(datosFactura);
    }

    /* @PostMapping("/crearXml")
    public String formarXml (@RequestBody DatosFactura datosFactura) throws Exception {
        return crearXml.formarXml(datosFactura);
    } */
    
}
