package com.ceag.facturacion.Service.Usuarios;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Usuarios.UsuariosDto;
import com.ceag.facturacion.Entity.Usuarios.UsuariosEntity;
import com.ceag.facturacion.Repository.UsuariosRepository;

@Service
public class UsuariosService {

    @Autowired
    UsuariosRepository usuariosRepository;

    public List<UsuariosDto> getByIdAndStatus(Long id, Boolean status){
        try {
            List<UsuariosEntity> usuariosEntity = usuariosRepository.findByIdAndStatus(id, status);
            List<UsuariosDto> usuariosDto = usuariosEntity.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
                
            return usuariosDto;
        } catch (Exception e) {
            return null;
        }
    }
    
    public UsuariosDto entityToDTO(UsuariosEntity usuario) {
        return new UsuariosDto(
                usuario);
    }

}
