package com.ceag.facturacion.Service.Catalogos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Entity.Catalogos.LocalidadEntity;
import com.ceag.facturacion.Repository.Catalogos.LocalidadRepository;

@Service
public class LocalidadService {
    @Autowired
    LocalidadRepository localidadRepository;

    public ResponseEntity<LocalidadEntity> addRegister(LocalidadEntity localidad){
        try {
            localidadRepository.save(localidad);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado la localidad" + e.getMessage());
        }
    }

    public ResponseEntity<LocalidadEntity> editRegister(Long id, LocalidadEntity localidad){
        Optional<LocalidadEntity> localidadId = localidadRepository.findById(id);
        
        if(localidadId.isPresent()){
            localidadRepository.save(localidad);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + localidad);
        }
    }

    public ResponseEntity<LocalidadEntity> editStatus(Long id, LocalidadEntity localidad){
        Optional<LocalidadEntity> localidadId = localidadRepository.findById(id);
        
        if(localidadId.isPresent()){
            LocalidadEntity localidadEntity = localidadId.get();
            localidadEntity.setStatus(localidad.getStatus());
            localidadRepository.save(localidadEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + localidad);
        }
    }
}
