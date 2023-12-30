package com.ceag.facturacion.Service.Catalogos;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Entity.Catalogos.NumPedAduanaEntity;
import com.ceag.facturacion.Repository.Catalogos.NumPedAduanaRepository;

@Service
public class NumPedAduanaService {
    @Autowired
    NumPedAduanaRepository numPedAduanaRepository;

    public ResponseEntity<NumPedAduanaEntity> addRegister(NumPedAduanaEntity numPedAduana){
        try {
            numPedAduanaRepository.save(numPedAduana);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el num ped aduana" + e.getMessage());
        }
    }

    public ResponseEntity<NumPedAduanaEntity> editRegister(Long id, NumPedAduanaEntity numPedAduana){
        Optional<NumPedAduanaEntity> numPedAduanaId = numPedAduanaRepository.findById(id);
        
        if(numPedAduanaId.isPresent()){
            numPedAduanaRepository.save(numPedAduana);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + numPedAduana);
        }
    }

    public ResponseEntity<NumPedAduanaEntity> editStatus(Long id, NumPedAduanaEntity numPedAduana){
        Optional<NumPedAduanaEntity> numPedAduanaId = numPedAduanaRepository.findById(id);
        
        if(numPedAduanaId.isPresent()){
            NumPedAduanaEntity numPedAduanaEntity = numPedAduanaId.get();
            numPedAduanaEntity.setStatus(numPedAduana.getStatus());
            numPedAduanaRepository.save(numPedAduanaEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + numPedAduana);
        }
    }
}
