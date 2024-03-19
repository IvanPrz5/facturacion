package com.ceag.facturacion.Controller.Usuarios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceag.facturacion.Auth.Utils.ResultObjectResponse;
import com.ceag.facturacion.Dto.Usuarios.UsuariosDto;
import com.ceag.facturacion.Entity.Usuarios.UsuariosEntity;
import com.ceag.facturacion.Service.Usuarios.UsuariosService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
        RequestMethod.PUT })
@RestController
@RequestMapping("/api/v1/Usuarios")
public class UsuariosController {

    @Autowired
    UsuariosService usuariosService;

    @GetMapping("/getEmpresas/{idUsuario}")
    public List<UsuariosDto> getMethodName(@PathVariable("idUsuario") Long idUsuario) {
        return usuariosService.getByIdAndStatus(idUsuario, true);
    }

    @GetMapping("/byStatus")
    public List<UsuariosDto> getByStatus() {
        return usuariosService.getByStatus(true);
    }

    @PostMapping("/asignar/{idUser}/{idEmpresa}")
    public String asignarEmpresa(@PathVariable("idUser") Long idUser, @PathVariable("idEmpresa") Long idEmpresa){
        return usuariosService.asignarEmpresa(idUser, idEmpresa);
    }

    @PostMapping("/quitar/{idUser}/{idEmpresa}")
    public String quitarEmpresa(@PathVariable("idUser") Long idUser, @PathVariable("idEmpresa") Long idEmpresa){
        return usuariosService.quitarEmpresa(idUser, idEmpresa);
    }

    @PostMapping("/edit/{idUser}")
    public ResultObjectResponse editarUser(@PathVariable("idUser") Long idUser, @RequestBody UsuariosEntity register){
        return usuariosService.editarUsuario(idUser, register);
    }
    
}
