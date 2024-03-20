package com.ceag.facturacion.Controller.Facturacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceag.facturacion.Dto.Facturacion.DatosCancelacionDto;
import com.ceag.facturacion.Dto.Facturacion.RespuestaTimbrado;
import com.ceag.facturacion.Service.Facturacion.FacturacionService;
import com.ceag.facturacion.Service.Facturacion.SwXmlService;
import com.ceag.facturacion.Utils.DatosFactura.DatosFactura;
import com.ceag.facturacion.Utils.Facturacion.JasperPdf;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/Facturacion")
public class FacturacionController {
    
    @Autowired
    FacturacionService facturacionService;

    @Autowired
    SwXmlService swXmlService;

    @Autowired
    JasperPdf jasperPdf;

    @PostMapping("/timbrarXml")
    public RespuestaTimbrado formarXml(@RequestBody DatosFactura datosFactura) throws Exception{ 
        return facturacionService.formarAndTimbrarXml(datosFactura);
    }

    @PostMapping("/vistaPrevia/{idEmpresa}")
    public byte[] descargarPdf(@PathVariable("idEmpresa") Long idEmpresa, @RequestBody DatosFactura datosFactura) throws Exception {
        return jasperPdf.crearPdf("XXXXXXXXXX", idEmpresa, datosFactura);
    }

    @PutMapping("/cancelarXml")
    public RespuestaTimbrado cancelarXml(@RequestBody DatosCancelacionDto comprobanteEntity){
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
