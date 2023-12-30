package com.ceag.facturacion.Service.Catalogos;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Entity.Catalogos.PaisEntity;
import com.ceag.facturacion.Repository.Catalogos.PaisRepository;
import com.ceag.facturacion.Utils.Catalogos.ConvertBasicDto;

@Service
public class PaisService {
    @Autowired
    PaisRepository paisRepository;

    public List<BasicDto> getRegisters(){
        try {
            List<PaisEntity> listPais = paisRepository.findByStatus(true);
            ConvertBasicDto convertBasicDto = new ConvertBasicDto();
            JSONArray jsonArray = new JSONArray(listPais);
            return convertBasicDto.getBasicDto(jsonArray);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fatal " + e.getMessage());
        }
    }

    public ResponseEntity<PaisEntity> addRegister(PaisEntity pais){
        try {
            paisRepository.save(pais);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el pais" + e.getMessage());
        }
    }

    public ResponseEntity<PaisEntity> editRegister(Long id, PaisEntity pais){
        Optional<PaisEntity> paisId = paisRepository.findById(id);
        
        if(paisId.isPresent()){
            paisRepository.save(pais);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + pais);
        }
    }

    public ResponseEntity<PaisEntity> editStatus(Long id, PaisEntity pais){
        Optional<PaisEntity> paisId = paisRepository.findById(id);
        
        if(paisId.isPresent()){
            PaisEntity paisEntity = paisId.get();
            paisEntity.setStatus(pais.getStatus());
            paisRepository.save(paisEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + pais);
        }
    }
}
