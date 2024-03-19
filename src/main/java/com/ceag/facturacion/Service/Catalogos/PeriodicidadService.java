package com.ceag.facturacion.Service.Catalogos;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Entity.Catalogos.PeriodicidadEntity;
import com.ceag.facturacion.Repository.Catalogos.PeriodicidadRepository;
import com.ceag.facturacion.Utils.Catalogos.ConvertDto;

@Service
public class PeriodicidadService {
    @Autowired
    PeriodicidadRepository periodicidadRepository;

    public List<BasicDto> getRegisters(){
        try {
            List<PeriodicidadEntity> listPeriodicidad = periodicidadRepository.findByStatus(true);
            ConvertDto convertBasicDto = new ConvertDto();
            JSONArray jsonArray = new JSONArray(listPeriodicidad);
            return convertBasicDto.getBasicDto(jsonArray);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fatal " + e.getMessage());
        }
    }

    public ResponseEntity<PeriodicidadEntity> addRegister(PeriodicidadEntity periodicidad){
        try {
            periodicidadRepository.save(periodicidad);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado la periodicidad" + e.getMessage());
        }
    }

    public ResponseEntity<PeriodicidadEntity> editRegister(Long id, PeriodicidadEntity periodicidad){
        Optional<PeriodicidadEntity> periodicidadId = periodicidadRepository.findById(id);
        
        if(periodicidadId.isPresent()){
            periodicidadRepository.save(periodicidad);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + periodicidad);
        }
    }

    public ResponseEntity<PeriodicidadEntity> editStatus(Long id, PeriodicidadEntity periodicidad){
        Optional<PeriodicidadEntity> periodicidadId = periodicidadRepository.findById(id);
        
        if(periodicidadId.isPresent()){
            PeriodicidadEntity periodicidadEntity = periodicidadId.get();
            periodicidadEntity.setStatus(periodicidad.getStatus());
            periodicidadRepository.save(periodicidadEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + periodicidad);
        }
    }
}
