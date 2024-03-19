package com.ceag.facturacion.Service.Catalogos;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Entity.Catalogos.AsentamientosEntity;
import com.ceag.facturacion.Repository.Catalogos.AsentamientosRepository;
import com.ceag.facturacion.Utils.Catalogos.ConvertDto;

@Service
public class AsentamientosService {
    @Autowired
    AsentamientosRepository asentamientosRepository;

    public List<BasicDto> getRegisters(){
        try {
            List<AsentamientosEntity> listAsentamientos = asentamientosRepository.findByStatus(true);
            ConvertDto convertBasicDto = new ConvertDto();
            JSONArray jsonArray = new JSONArray(listAsentamientos);
            return convertBasicDto.getBasicDto(jsonArray);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fatal " + e.getMessage());
        }
    }

    public ResponseEntity<AsentamientosEntity> addRegister(AsentamientosEntity asentamientos){
        try {
            asentamientosRepository.save(asentamientos);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el asentamiento" + e.getMessage());
        }
    }

    public ResponseEntity<AsentamientosEntity> editRegister(Long id, AsentamientosEntity asentamientos){
        Optional<AsentamientosEntity> asentamientosId = asentamientosRepository.findById(id);
        
        if(asentamientosId.isPresent()){
            asentamientosRepository.save(asentamientos);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + asentamientos);
        }
    }

    public ResponseEntity<AsentamientosEntity> editStatus(Long id, AsentamientosEntity asentamientos){
        Optional<AsentamientosEntity> asentamientosId = asentamientosRepository.findById(id);

        if(asentamientosId.isPresent()){
            AsentamientosEntity asentamientosEntity = asentamientosId.get();
            asentamientosEntity.setStatus(asentamientos.getStatus());
            asentamientosRepository.save(asentamientosEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al eliminar " + asentamientos);
        }
    }
}
