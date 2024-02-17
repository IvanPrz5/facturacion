package com.ceag.facturacion.Controller.Facturacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceag.facturacion.Service.Facturacion.XmlService;
import com.ceag.facturacion.Utils.Facturacion.JasperPdf;

import org.springframework.core.io.Resource;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/Xml")
public class XmlController {
    
    @Autowired
    XmlService xmlService;

    @Autowired
    JasperPdf jasperPdf;

    @GetMapping("/descargarXml/{uuid}")
    public ResponseEntity<Resource> descargarXml(@PathVariable("uuid") String uuid) throws Exception {
        return xmlService.descargarXml(uuid);
    }

    @GetMapping("/descargarCvv/{uuid}")
    public ResponseEntity<Resource> descargarCvv(@PathVariable("uuid") String uuid) throws Exception {
        return xmlService.descargarCvv(uuid);
    }

    @GetMapping("/descargarPdf/{uuid}/{idEmpresa}")
    public ResponseEntity<Resource> descargarPdf(@PathVariable("uuid") String uuid, @PathVariable("idEmpresa") Long idEmpresa) throws Exception {
        return jasperPdf.crearPdf(uuid, idEmpresa);
    }
}
