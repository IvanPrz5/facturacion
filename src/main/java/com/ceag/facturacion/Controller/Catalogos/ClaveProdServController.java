package com.ceag.facturacion.Controller.Catalogos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceag.facturacion.Dto.Catalogos.ClaveProdServDto;
import com.ceag.facturacion.Entity.Busqueda.DivisionEntity;
import com.ceag.facturacion.Entity.Busqueda.GruposEntity;
import com.ceag.facturacion.Entity.Catalogos.ClaveProdServEntity;
import com.ceag.facturacion.Service.Catalogos.ClaveProdServService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("api/v1/ClaveProdServ")
public class ClaveProdServController {
    @Autowired
    ClaveProdServService claveProdServService;

    @GetMapping("/byCod/{cod}")
    public List<ClaveProdServDto> getRegisters(@PathVariable("cod") String codigo) {
        return claveProdServService.getRegistersByCodigo(codigo);
    }

    @PostMapping("/add")
    public ResponseEntity<ClaveProdServEntity> addRegister(@RequestBody ClaveProdServEntity claveProdServ) {
        return claveProdServService.addRegister(claveProdServ);
    }
    
    @PutMapping("/edit/{id}")
    public ResponseEntity<ClaveProdServEntity> editRegister(@PathVariable("id") Long id, @RequestBody ClaveProdServEntity claveProdServ) {
        return claveProdServService.editRegister(id, claveProdServ);
    }

    @PutMapping("/delete/{id}")
    public ResponseEntity<ClaveProdServEntity> editStatus(@PathVariable("id") Long id, @RequestBody ClaveProdServEntity claveProdServ) {
        return claveProdServService.editStatus(id, claveProdServ);
    }

    @GetMapping("/division/{status}")
    public List<DivisionEntity> getApi (@PathVariable("status") Boolean status) throws Exception{
        return claveProdServService.getApi(status);
    }

    @GetMapping("/grupos/{division}")
    public List<GruposEntity> getByDivision(@PathVariable("division") String division) throws Exception{
        return claveProdServService.getByDivision(division);
    }

    @GetMapping("/clase/{division}")
    public List<ClaveProdServDto> getClase(@PathVariable("division") String division) throws Exception{
        return claveProdServService.getClase(division);
    }

    @GetMapping("/productos/{productos}")
    public List<ClaveProdServDto> getProductos(@PathVariable("productos") String productos) throws Exception{
        return claveProdServService.getProductos(productos);
    }
}
