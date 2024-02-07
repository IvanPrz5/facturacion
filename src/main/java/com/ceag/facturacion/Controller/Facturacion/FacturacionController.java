package com.ceag.facturacion.Controller.Facturacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceag.facturacion.Dto.Facturacion.RespuestaTimbrado;
import com.ceag.facturacion.Entity.Facturacion.ComprobanteEntity;
import com.ceag.facturacion.Service.Facturacion.CrearXmlService;
import com.ceag.facturacion.Service.Facturacion.SwXmlService;
import com.ceag.facturacion.Utils.DatosFactura.DatosFactura;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/Facturacion")
public class FacturacionController {
    
    @Autowired
    CrearXmlService crearXmlService;

    @Autowired
    SwXmlService swXmlService;

    @PostMapping("/timbrarXml")
    public RespuestaTimbrado formarXml(@RequestBody DatosFactura datosFactura) throws Exception{ 
        return crearXmlService.formarAndTimbrarXml(datosFactura);
    }

    @PostMapping("/cancelarXml")
    public RespuestaTimbrado cancelarXml(@RequestBody ComprobanteEntity comprobanteEntity){
        return swXmlService.cancelarXml(comprobanteEntity);
    }
    

    /* @PostMapping("/timbrarDespues")
    public RespuestaTimbrado timbrarDespues(@RequestBody DatosFactura datosFactura) throws Exception{ 
        return crearXmlService.timbrarDespues(datosFactura);
    } */

    /* @PostMapping("/crearXml")
    public String formarXml (@RequestBody DatosFactura datosFactura) throws Exception {
        return crearXml.formarXml(datosFactura);
    } */
}
