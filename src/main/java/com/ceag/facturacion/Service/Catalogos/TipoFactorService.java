package com.ceag.facturacion.Service.Catalogos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Entity.Catalogos.TipoFactorEntity;
import com.ceag.facturacion.Repository.Catalogos.TipoFactorRepository;

@Service
public class TipoFactorService {
    @Autowired
    TipoFactorRepository tipoFactorRepository;

    public ResponseEntity<List<BasicDto>> getByStatus(){
        try {
            List<TipoFactorEntity> listTipoFactor = tipoFactorRepository.findByStatus(true);
            List<BasicDto> lista = new ArrayList<>();
            for(int i=0; i<listTipoFactor.size(); i++){
                BasicDto basicDto = new BasicDto();
                basicDto.setId(listTipoFactor.get(i).getId());
                basicDto.setCodigo(listTipoFactor.get(i).getCodigo());
                basicDto.setDescripcion("No Aplica");
                lista.add(basicDto);
            }
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error en tipo factor " + e.getMessage());
        }
    }

    public ResponseEntity<TipoFactorEntity> addRegister(TipoFactorEntity tipoFactor){
        try {
            tipoFactorRepository.save(tipoFactor);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el tipo factor" + e.getMessage());
        }
    }

    public ResponseEntity<TipoFactorEntity> editRegister(Long id, TipoFactorEntity tipoFactor){
        Optional<TipoFactorEntity> tipoFactorId = tipoFactorRepository.findById(id);

        if(tipoFactorId.isPresent()){
            tipoFactorRepository.save(tipoFactor);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + tipoFactor);
        }
    }

    public ResponseEntity<TipoFactorEntity> editStatus(Long id, TipoFactorEntity tipoFactor){
        Optional<TipoFactorEntity> tipoFactorId = tipoFactorRepository.findById(id);

        if(tipoFactorId.isPresent()){
            TipoFactorEntity tipoFactorEntity = tipoFactorId.get();
            tipoFactorEntity.setStatus(tipoFactor.getStatus());
            tipoFactorRepository.save(tipoFactorEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + tipoFactor);
        }
    }
}
