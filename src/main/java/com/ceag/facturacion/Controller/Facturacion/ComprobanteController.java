package com.ceag.facturacion.Controller.Facturacion;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ceag.facturacion.Dto.Facturacion.FacturasDto;
import com.ceag.facturacion.Entity.Empresas.EmpresasEntity;
import com.ceag.facturacion.Repository.Facturacion.ComprobanteRepository;
import com.ceag.facturacion.Service.Facturacion.ComprobanteService;
import com.ceag.facturacion.Utils.DatosFactura.DatosFactura;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
        RequestMethod.PUT })
@RestController
@RequestMapping("/api/v1/Comprobante")
public class ComprobanteController {
    @Autowired
    ComprobanteService comprobanteService;

    @Autowired
    ComprobanteRepository comprobanteRepository;

    /* @PostMapping("/algo")
    public void getImage() throws IOException{
        byte[] fileContent = Files.readAllBytes(Paths.get("C:/Users/SISTEMAS/Desktop/facturacion/src/main/resources/cfdi/jasper/fondo.png"));
        String base64 = Base64.getEncoder().encodeToString(fileContent);
        System.out.println(base64);
    } */

    @GetMapping("/{tipo}/{isCancelado}/{idEmpresa}/pageable")
    public Page<FacturasDto> paginacion(@PathVariable("tipo") Boolean tipo,
            @PathVariable("isCancelado") Boolean cancelado, @PathVariable("idEmpresa") EmpresasEntity idEmpresa,
            Pageable pageable) throws Exception {
        return comprobanteService.paginacionFacturas(tipo, cancelado, idEmpresa, pageable);
    }

    @GetMapping("/byUuid/{uuid}/{tipo}/{isCancelado}/{idEmpresa}")
    public Page<FacturasDto> getByUuid(@PathVariable("uuid") String uuid, @PathVariable("tipo") Boolean tipo,
            @PathVariable("isCancelado") Boolean isCancelado,
            @PathVariable("idEmpresa") EmpresasEntity idEmpresa, @RequestParam("page") int page, @RequestParam("size") int size) {
        return comprobanteService.getByUuuid(uuid, tipo, isCancelado, idEmpresa, page, size);
    }

    @GetMapping("/byFechas/{inicio}/{fin}/{tipo}/{isCancelado}/{idEmpresa}")
    public Page<FacturasDto> getByFechas(@PathVariable("inicio") String inicio, @PathVariable("fin") String fin,
            @PathVariable("tipo") Boolean tipo, @PathVariable("isCancelado") Boolean isCancelado,
            @PathVariable("idEmpresa") EmpresasEntity idEmpresa, @RequestParam("page") int page, @RequestParam("size") int size) {
        return comprobanteService.getByFechas(inicio, fin, tipo, isCancelado, idEmpresa, page, size);
    }

    @GetMapping("/byTotal/{total}/{tipo}/{isCancelado}/{idEmpresa}")
    public Page<FacturasDto> getByTotales(@PathVariable("total") Double total, @PathVariable("tipo") Boolean tipo,
            @PathVariable("isCancelado") Boolean isCancelado,
            @PathVariable("idEmpresa") EmpresasEntity idEmpresa, @RequestParam("page") int page, @RequestParam("size") int size) {
        return comprobanteService.getByTotales(total, tipo, isCancelado, idEmpresa, page, size);
    }

    @PutMapping("/eliminar")
    public ResponseEntity<Long> eliminarFactura(@RequestBody DatosFactura datosFactura) {
        return comprobanteService.eliminarFactura(datosFactura);
    }
    // return comprobanteService.eliminarFactura(datosFactura);
}
