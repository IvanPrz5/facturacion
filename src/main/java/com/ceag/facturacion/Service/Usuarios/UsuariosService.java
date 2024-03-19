package com.ceag.facturacion.Service.Usuarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Auth.Utils.ResultObjectResponse;
import com.ceag.facturacion.Dto.Empresas.EmpresasDto;
import com.ceag.facturacion.Dto.Usuarios.UsuariosDto;
import com.ceag.facturacion.Entity.Usuarios.UsuariosEntity;
import com.ceag.facturacion.Repository.Usuarios.UsuariosRepository;

@Service
public class UsuariosService {

    @Autowired
    UsuariosRepository usuariosRepository;

    public List<UsuariosDto> getByIdAndStatus(Long id, Boolean status){
        try {
            List<UsuariosEntity> usuariosEntity = usuariosRepository.findByIdAndStatus(id, status);
            List<UsuariosDto> usuariosDtos = new ArrayList<>();
            List<EmpresasDto> lista = new ArrayList<>();
            for(int i=0; i<usuariosEntity.size(); i++){
                UsuariosDto user = new UsuariosDto();
                user.setId(usuariosEntity.get(i).getId());
                user.setNombre(usuariosEntity.get(i).getNombre());
                user.setApPaterno(usuariosEntity.get(i).getApPaterno());
                user.setEmail(usuariosEntity.get(i).getEmail());
                user.setAvatar(usuariosEntity.get(i).getAvatar());
                user.setRole(usuariosEntity.get(i).getRoles());
                for(int j=0; j<usuariosEntity.get(i).getEmpresas().size(); j++){
                    EmpresasDto empresasDto = new EmpresasDto();
                    empresasDto.setId(usuariosEntity.get(i).getEmpresas().get(j).getId());
                    empresasDto.setNombre(usuariosEntity.get(i).getEmpresas().get(j).getNombre());
                    empresasDto.setRfc(usuariosEntity.get(i).getEmpresas().get(j).getRfc());
                    empresasDto.setLogo(usuariosEntity.get(i).getEmpresas().get(j).getLogo());
                    empresasDto.setCodPostal(usuariosEntity.get(i).getEmpresas().get(j).getCodPostal());
                    lista.add(empresasDto);
                    user.setEmpresas(lista);
                }
                usuariosDtos.add(user);
            }
            return usuariosDtos;
        } catch (Exception e) {
            return null;
        }
    }

    public List<UsuariosDto> getByStatus(Boolean status){
        try {
            List<UsuariosEntity> usuariosEntity = usuariosRepository.findByStatusOrderById(status);
            List<UsuariosDto> usuariosDto = usuariosEntity.stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
                
            return usuariosDto;
        } catch (Exception e) {
            return null;
        }
    }

    public String asignarEmpresa(Long idUser, Long idEmpresa){
        try {
            try {
                usuariosRepository.insertIntoUsuarioEmpresa(idUser, idEmpresa);
            } catch (Exception e){ 
                return "0";
            }
            return null;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }
    
    public String quitarEmpresa(Long idUser, Long idEmpresa){
        try {
            try {
                usuariosRepository.deleteUsuarioEmpresa(idUser, idEmpresa);
            } catch (Exception e){ 
                return "0";
            }
            return null;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public UsuariosDto entityToDTO(UsuariosEntity usuario) {
        return new UsuariosDto(usuario);
    }

    public ResultObjectResponse editarUsuario(Long idUser, UsuariosEntity register){
        try {
            Optional<UsuariosEntity> usuario = usuariosRepository.findById(idUser);
            if(usuario.isPresent()){
                UsuariosEntity editUsuario = new UsuariosEntity();
                editUsuario.setId(usuario.get().getId());
                editUsuario.setNombre(register.getNombre());
                editUsuario.setApPaterno(register.getApPaterno());
                editUsuario.setEmail(register.getEmail());
                editUsuario.setPassword(usuario.get().getPassword());
                editUsuario.setAvatar(usuario.get().getPassword());
                editUsuario.setStatus(usuario.get().getStatus());
                usuariosRepository.save(editUsuario);
            }
            return new ResultObjectResponse(0, false, "El usuario se registro con exito", null);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

}
