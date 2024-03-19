package com.ceag.facturacion.Service.Empresas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.ceag.facturacion.Dto.Empresas.EmpresasDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ceag.facturacion.Entity.Empresas.EmpresasEntity;
import com.ceag.facturacion.Repository.Empresas.EmpresasRepository;
import com.ceag.facturacion.Repository.Usuarios.UsuariosRepository;
import com.ceag.facturacion.Utils.Facturacion.CadenaOriginal;

@Service
public class EmpresasService {
    @Autowired
    EmpresasRepository empresasRepository;

    @Autowired
    UsuariosRepository usuariosRepository;

    public List<EmpresasDto> getAll() throws Exception{
        try {
            List<EmpresasEntity> empresas = empresasRepository.findByStatus(true);
            List<EmpresasDto> list = new ArrayList<>();
            for(int i=0; i<empresas.size(); i++){
                EmpresasDto empresasDto = new EmpresasDto();
                empresasDto.setId(empresas.get(i).getId());
                empresasDto.setNombre(empresas.get(i).getNombre());
                empresasDto.setRfc(empresas.get(i).getRfc());
                empresasDto.setLogo(empresas.get(i).getLogo());
                empresasDto.setCodPostal(empresas.get(i).getCodPostal());
                list.add(empresasDto);
            }
            return list;
        } catch (Exception e) {
            throw new Exception("Error al obtener empresa" + e.getMessage());
        }
    }

    public ResponseEntity<EmpresasEntity> addEmpresa(List<MultipartFile> multipartFile, EmpresasEntity entity, Long idUsuario){
        try {
            EmpresasEntity empresa = new EmpresasEntity();
            empresa.setNombre(entity.getNombre());
            empresa.setRfc(entity.getRfc());
            empresa.setCurp(entity.getCurp());
            empresa.setCodPostal(entity.getCodPostal());
            empresa.setUsuarioPac(entity.getUsuarioPac());
            empresa.setContraseñaPac(entity.getContraseñaPac());
            empresa.setIdRegimenFiscal(entity.getIdRegimenFiscal());
            empresa.setFisica(entity.getFisica());
            empresa.setPassKey(entity.getPassKey());
            String cerB64 = fileToB64(multipartFile.get(0));
            empresa.setCerB64(cerB64);
            empresa.setKeyB64(fileToB64(multipartFile.get(1)));

            CadenaOriginal cadenaOriginal = new CadenaOriginal();
            empresa.setNumCertificado(cadenaOriginal.getNoCertificado(cerB64));
            if(multipartFile.size() > 2){
                empresa.setLogo(fileToB64(multipartFile.get(2)));
            }else{
                empresa.setLogo(null);
            }
            empresa.setStatus(true);

            EmpresasEntity empresaId = empresasRepository.save(empresa);

            // if(empresaId != null){
                try {
                    usuariosRepository.insertIntoUsuarioEmpresa(idUsuario, empresaId.getId());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            // }

            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se agrego la empresa " + e.getMessage());
        }
    }

    public List<EmpresasDto> getEmpresasByJoin(Long idUser) throws Exception{
        try {

            List<EmpresasEntity> empresas = empresasRepository.queryJoin(idUser);
            List<EmpresasDto> list = new ArrayList<>();
            for(int i=0; i<empresas.size(); i++){
                EmpresasDto empresasDto = new EmpresasDto();
                empresasDto.setId(empresas.get(i).getId());
                empresasDto.setNombre(empresas.get(i).getNombre());
                empresasDto.setRfc(empresas.get(i).getRfc());
                empresasDto.setLogo("No Aplica");
                empresasDto.setCodPostal(empresas.get(i).getCodPostal());
                list.add(empresasDto);
            }
            
            return list;
        } catch (Exception e) {
            throw new Exception("Error al obtener empresa" + e.getMessage());
        }
    }

    public String fileToB64(MultipartFile multipartFile) throws IOException{
        try {
            byte[] fileContent = multipartFile.getBytes();
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (Exception e) {
            throw new IOException("Error Base 64" + e.getMessage());
        }
    }
}
