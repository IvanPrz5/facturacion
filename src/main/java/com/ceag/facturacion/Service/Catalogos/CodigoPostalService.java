package com.ceag.facturacion.Service.Catalogos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Entity.Catalogos.CodigoPostalEntity;
import com.ceag.facturacion.Repository.Catalogos.CodigoPostalRepository;

@Service
public class CodigoPostalService {
    @Autowired
    CodigoPostalRepository codigoPostalRepository;

    public ResponseEntity<List<BasicDto>> getCodigoPostal(Long id){
        try {
            Optional<CodigoPostalEntity> codigoPosId = codigoPostalRepository.findById(id);
            BasicDto basicDto = new BasicDto();
            List<BasicDto> lista = new ArrayList<>();
            basicDto.setId(codigoPosId.get().getId());
            basicDto.setDescripcion(codigoPosId.get().getIdEstado().getNombreEstado());
            basicDto.setCodigo("No aplica");
            lista.add(basicDto);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            throw new IllegalArgumentException("El codigo postal no existe" + e.getMessage());
        }
    }

    public ResponseEntity<CodigoPostalEntity> addRegister(CodigoPostalEntity codigoPostal){
        try {
            codigoPostalRepository.save(codigoPostal);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado la codigo postal" + e.getMessage());
        }
    }

    public ResponseEntity<CodigoPostalEntity> editRegister(Long id, CodigoPostalEntity codigoPostal){
        Optional<CodigoPostalEntity> codigoPostalId = codigoPostalRepository.findById(id);
        
        if(codigoPostalId.isPresent()){
            codigoPostalRepository.save(codigoPostal);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + codigoPostal);
        }
    }

    public ResponseEntity<CodigoPostalEntity> editStatus(Long id, CodigoPostalEntity codigoPostal){
        Optional<CodigoPostalEntity> codigoPostalId = codigoPostalRepository.findById(id);
        
        if(codigoPostalId.isPresent()){
            CodigoPostalEntity codigoPostalEntity = codigoPostalId.get();
            codigoPostalEntity.setStatus(codigoPostal.getStatus());
            codigoPostalRepository.save(codigoPostalEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + codigoPostal);
        }
    }
}
