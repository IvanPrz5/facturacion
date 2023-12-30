package com.ceag.facturacion.Service.Catalogos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Entity.Catalogos.PatenteAduanalEntity;
import com.ceag.facturacion.Repository.Catalogos.PatenteAduanalRepository;

@Service
public class PatenteAduanalService {
    @Autowired
    PatenteAduanalRepository patenteAduanalRepository;

    public ResponseEntity<PatenteAduanalEntity> addRegister(PatenteAduanalEntity patenteAduanal){
        try {
            patenteAduanalRepository.save(patenteAduanal);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado la patente aduanal" + e.getMessage());
        }
    }

    public ResponseEntity<PatenteAduanalEntity> editRegister(Long id, PatenteAduanalEntity patenteAduanal){
        Optional<PatenteAduanalEntity> patenteAduanalId = patenteAduanalRepository.findById(id);
        
        if(patenteAduanalId.isPresent()){
            patenteAduanalRepository.save(patenteAduanal);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + patenteAduanal);
        }
    }

    public ResponseEntity<PatenteAduanalEntity> editStatus(Long id, PatenteAduanalEntity patenteAduanal){
        Optional<PatenteAduanalEntity> patenteAduanalId = patenteAduanalRepository.findById(id);
        
        if(patenteAduanalId.isPresent()){
            PatenteAduanalEntity patenteAduanalEntity = patenteAduanalId.get();
            patenteAduanalEntity.setStatus(patenteAduanal.getStatus());
            patenteAduanalRepository.save(patenteAduanalEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + patenteAduanal);
        }
    }
}
