package com.ceag.facturacion.Service.Catalogos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Entity.Catalogos.MunicipioEntity;
import com.ceag.facturacion.Repository.Catalogos.MunicipioRepository;

@Service
public class MunicipioService {
    @Autowired
    MunicipioRepository municipioRepository;

    public ResponseEntity<MunicipioEntity> addRegister(MunicipioEntity municipio){
        try {
            municipioRepository.save(municipio);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el municipio" + e.getMessage());
        }
    }

    public ResponseEntity<MunicipioEntity> editRegister(Long id, MunicipioEntity municipio){
        Optional<MunicipioEntity> municipioId = municipioRepository.findById(id);
        
        if(municipioId.isPresent()){
            municipioRepository.save(municipio);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + municipio);
        }
    }

    public ResponseEntity<MunicipioEntity> editStatus(Long id, MunicipioEntity municipio){
        Optional<MunicipioEntity> municipioId = municipioRepository.findById(id);
        
        if(municipioId.isPresent()){
            MunicipioEntity municipioEntity = municipioId.get();
            municipioEntity.setStatus(municipio.getStatus());
            municipioRepository.save(municipioEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + municipio);
        }
    }
}
