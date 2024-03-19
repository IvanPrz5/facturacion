package com.ceag.facturacion.Controller.Usuarios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ceag.facturacion.Entity.Usuarios.RolesEntity;
import com.ceag.facturacion.Service.Usuarios.RolesService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE,
    RequestMethod.PUT })
@RestController
@RequestMapping("/api/v1/Roles")
public class RolesController {
    
    @Autowired
    RolesService rolesService;

    @GetMapping("/byUser/{idUser}")
    public List<RolesEntity> byUser(@PathVariable("idUser") Long idUser) {
        return rolesService.getRolesByUser(idUser);
    }
    
    @GetMapping("/all")
    public List<RolesEntity> getAll() {
        return rolesService.getRoles();
    }

    @PostMapping("/addRol/{idRole}/{idUser}")
    public Long addRole(@PathVariable("idRole") Long idRole, @PathVariable("idUser") Long idUser) {
        return rolesService.insertIntoUserRoles(idRole, idUser);
    }

    @PostMapping("/deleteRol/{idRole}/{idUser}")
    public Long deleteRole(@PathVariable("idRole") Long idRole, @PathVariable("idUser") Long idUser) {
        return rolesService.deleteUserRoles(idRole, idUser);
    }
}
