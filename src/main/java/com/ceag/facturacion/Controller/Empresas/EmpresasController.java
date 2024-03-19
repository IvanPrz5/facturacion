package com.ceag.facturacion.Controller.Empresas;

import java.util.List;

import com.ceag.facturacion.Dto.Empresas.EmpresasDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;

import com.ceag.facturacion.Entity.Empresas.EmpresasEntity;
import com.ceag.facturacion.Service.Empresas.EmpresasService;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT, })
@RestController
@RequestMapping("/api/v1/Empresas")
public class EmpresasController {
    @Autowired
    EmpresasService empresasService;

    @GetMapping("/getAll")
    public List<EmpresasDto> getAll() throws Exception {
        return empresasService.getAll();
    }

    @GetMapping("/getEmpresas/{idUser}")
    public List<EmpresasDto> getEmpresas(@PathVariable("idUser") Long idUser) throws Exception {
        return empresasService.getEmpresasByJoin(idUser);
    }

    @PostMapping("/add/{idUsuario}")
    public ResponseEntity<EmpresasEntity> addEmpresa(@RequestPart("file") List<MultipartFile> multipartFile, @RequestPart("doc") EmpresasEntity empresa, @PathVariable("idUsuario") Long idUsuario){
        return empresasService.addEmpresa(multipartFile, empresa, idUsuario);
    }

    @PostMapping("/editar/{idEmpresa}")
    public ResponseEntity<EmpresasEntity> editEmpresa(@RequestPart("file") List<MultipartFile> multipartFile, @RequestPart("doc") EmpresasEntity empresa, @PathVariable("idEmpresa") Long idEmpresa) throws Exception{
        return empresasService.editEmpresa(multipartFile, empresa, idEmpresa);
    }
}