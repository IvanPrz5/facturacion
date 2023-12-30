package com.ceag.facturacion.Service.Catalogos;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Entity.Catalogos.EstadoEntity;
import com.ceag.facturacion.Entity.Catalogos.PaisEntity;
import com.ceag.facturacion.Repository.Catalogos.EstadoRepository;
import com.ceag.facturacion.Repository.Catalogos.PaisRepository;
import com.ceag.facturacion.Utils.Catalogos.ConvertBasicDto;

@Service
public class EstadoService {
    @Autowired
    PaisRepository paisRepository;

    @Autowired
    EstadoRepository estadoRepository;

    public List<BasicDto> getRegisters(Long id){
        try {
            Optional<PaisEntity> paisEntity = paisRepository.findById(id); 
            List<EstadoEntity> listEstado = estadoRepository.findByIdPais(paisEntity.get());
            ConvertBasicDto convertBasicDto = new ConvertBasicDto();
            JSONArray jsonArray = new JSONArray(listEstado);
            return convertBasicDto.getBasicDto(jsonArray);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fatal " + e.getMessage());
        }
    }

    public ResponseEntity<EstadoEntity> addRegister(EstadoEntity estado){
        try {
            estadoRepository.save(estado);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el estado" + e.getMessage());
        }
    }

    public ResponseEntity<EstadoEntity> editRegister(Long id, EstadoEntity estado){
        Optional<EstadoEntity> estadoId = estadoRepository.findById(id);
        
        if(estadoId.isPresent()){
            estadoRepository.save(estado);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + estado);
        }
    }

    public ResponseEntity<EstadoEntity> editStatus(Long id, EstadoEntity estado){
        Optional<EstadoEntity> estadoId = estadoRepository.findById(id);
        
        if(estadoId.isPresent()){
            EstadoEntity estadoEntity = estadoId.get();
            estadoEntity.setStatus(estado.getStatus());
            estadoRepository.save(estadoEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + estado);
        }
    }
}
