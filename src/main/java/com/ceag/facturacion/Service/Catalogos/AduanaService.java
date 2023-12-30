package com.ceag.facturacion.Service.Catalogos;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Entity.Catalogos.AduanaEntity;
import com.ceag.facturacion.Repository.Catalogos.AduanaRepository;
import com.ceag.facturacion.Utils.Catalogos.ConvertBasicDto;

@Service
public class AduanaService {
    @Autowired
    AduanaRepository aduanaRepository;

    public List<BasicDto> getRegisters(){
        try {
            List<AduanaEntity> listAduana = aduanaRepository.findByStatus(true);
            ConvertBasicDto convertBasicDto = new ConvertBasicDto();
            JSONArray jsonArray = new JSONArray(listAduana);
            return convertBasicDto.getBasicDto(jsonArray);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fatal " + e.getMessage());
        }
    }

    public ResponseEntity<AduanaEntity> addRegister(AduanaEntity aduana){
        try {
            aduanaRepository.save(aduana);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado la aduana" + e.getMessage());
        }
    }

    public ResponseEntity<AduanaEntity> editRegister(Long id, AduanaEntity aduana){
        Optional<AduanaEntity> aduanaId = aduanaRepository.findById(id);
        
        if(aduanaId.isPresent()){
            aduanaRepository.save(aduana);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + aduana);
        }
    }

    public ResponseEntity<AduanaEntity> editStatus(Long id, AduanaEntity aduana){
        Optional<AduanaEntity> aduanaId = aduanaRepository.findById(id);
        
        if(aduanaId.isPresent()){
            AduanaEntity aduanaEntity = aduanaId.get();
            aduanaEntity.setStatus(aduana.getStatus());
            aduanaRepository.save(aduanaEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al eliminar " + aduana);
        }
    }
}
