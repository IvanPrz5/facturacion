package com.ceag.facturacion.Service.Usuarios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Entity.Usuarios.RolesEntity;
import com.ceag.facturacion.Repository.Usuarios.RolesRepository;
import com.ceag.facturacion.Repository.Usuarios.UsuariosRepository;

@Service
public class RolesService {
    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    UsuariosRepository usuariosRepository;

    public List<RolesEntity> getRoles(){
        try {
            return rolesRepository.findByStatus(true);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public List<RolesEntity> getRolesByUser(Long idUser){
        try {
            return rolesRepository.queryJoin(idUser);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public Long insertIntoUserRoles(Long idRole, Long idUser){
        try {
            try {
                usuariosRepository.insertIntoUserRoles(idRole, idUser);
            } catch (Exception e) {
                return 0L;
            }
            return null;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public Long deleteUserRoles(Long idRole, Long idUser){
        try {
            try {
                rolesRepository.deleteUserRole(idRole, idUser);
            } catch (Exception e) {
                return 0L;
            }
            return null;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
}
