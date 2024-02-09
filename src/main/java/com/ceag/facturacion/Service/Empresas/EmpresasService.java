package com.ceag.facturacion.Service.Empresas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.ceag.facturacion.Dto.Empresas.EmpresasDto;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.ceag.facturacion.Entity.Empresas.EmpresasEntity;
import com.ceag.facturacion.Repository.Empresas.EmpresasRepository;

@Service
public class EmpresasService {
    @Autowired
    EmpresasRepository empresasRepository;

    public List<EmpresasDto> getAll(){
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
            throw new IllegalArgumentException("Error al obtener empresa" + e.getMessage());
        }
    }

    public ResponseEntity<EmpresasEntity> addEmpresa(List<MultipartFile> multipartFile, EmpresasEntity entity){
        try {
            EmpresasEntity empresa = new EmpresasEntity();
            empresa.setNombre(entity.getNombre());
            empresa.setRfc(entity.getRfc());
            empresa.setCurp(entity.getCurp());
            empresa.setCodPostal(entity.getCodPostal());
            empresa.setNumCertificado(entity.getNumCertificado());
            empresa.setUsuarioPac(entity.getUsuarioPac());
            empresa.setContraseñaPac(entity.getContraseñaPac());
            empresa.setIdRegimenFiscal(entity.getIdRegimenFiscal());
            empresa.setFisica(entity.getFisica());
            empresa.setPassKey(entity.getPassKey());
            empresa.setCerB64(fileToB64(multipartFile.get(0)));
            empresa.setKeyB64(fileToB64(multipartFile.get(1)));
            empresa.setLogo(fileToB64(multipartFile.get(2)));
            empresa.setStatus(true);

            empresasRepository.save(empresa);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se agrego la empresa " + e.getMessage());
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