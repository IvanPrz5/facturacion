package com.ceag.facturacion.Dto.Usuarios;

import java.util.ArrayList;
import java.util.List;

import com.ceag.facturacion.Dto.Empresas.EmpresasDto;
import com.ceag.facturacion.Entity.Usuarios.RolesEntity;
import com.ceag.facturacion.Entity.Usuarios.UsuariosEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuariosDto {
    private Long id;
    private String nombre;
    private String apPaterno;
    private String email;
    private String avatar;
    private List<RolesEntity> role = new ArrayList<>();
    private List<EmpresasDto> empresas = new ArrayList<>();

    public UsuariosDto(UsuariosEntity usuario){
        this.id = usuario.getId();
        this.nombre = usuario.getNombre();
        this.apPaterno = usuario.getApPaterno();
        this.email = usuario.getEmail();
        this.avatar = usuario.getAvatar();
        this.role = usuario.getRoles();
    }
}
