package com.ceag.facturacion.Service.Catalogos;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Entity.Catalogos.ObjetoImpEntity;
import com.ceag.facturacion.Repository.Catalogos.ObjetoImpRepository;
import com.ceag.facturacion.Utils.Catalogos.ConvertDto;

@Service
public class ObjetoImpService {
    @Autowired
    ObjetoImpRepository objetoImpRepository;

    public List<BasicDto> getRegisters(){
        try {
            List<ObjetoImpEntity> listObjetoImp = objetoImpRepository.findByStatus(true);
            ConvertDto convertBasicDto = new ConvertDto();
            JSONArray jsonArray = new JSONArray(listObjetoImp);
            return convertBasicDto.getBasicDto(jsonArray);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fatal " + e.getMessage());
        }
    }

    public ResponseEntity<ObjetoImpEntity> addRegister(ObjetoImpEntity objetoImp){
        try {
            objetoImpRepository.save(objetoImp);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el objeto imp" + e.getMessage());
        }
    }

    public ResponseEntity<ObjetoImpEntity> editRegister(Long id, ObjetoImpEntity objetoImp){
        Optional<ObjetoImpEntity> objetoImpId = objetoImpRepository.findById(id);
        
        if(objetoImpId.isPresent()){
            objetoImpRepository.save(objetoImp);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + objetoImp);
        }
    }

    public ResponseEntity<ObjetoImpEntity> editStatus(Long id, ObjetoImpEntity objetoImp){
        Optional<ObjetoImpEntity> objetoImpId = objetoImpRepository.findById(id);
        
        if(objetoImpId.isPresent()){
            ObjetoImpEntity objetoImpEntity = objetoImpId.get();
            objetoImpEntity.setStatus(objetoImp.getStatus());
            objetoImpRepository.save(objetoImpEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + objetoImp);
        }
    }
}
