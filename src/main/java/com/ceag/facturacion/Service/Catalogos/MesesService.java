package com.ceag.facturacion.Service.Catalogos;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Entity.Catalogos.MesesEntity;
import com.ceag.facturacion.Repository.Catalogos.MesesRepository;
import com.ceag.facturacion.Utils.Catalogos.ConvertDto;

@Service
public class MesesService {
    @Autowired
    MesesRepository mesesRepository;

    public List<BasicDto> getRegisters(){
        try {
            List<MesesEntity> listMeses = mesesRepository.findByStatus(true);
            ConvertDto convertBasicDto = new ConvertDto();
            JSONArray jsonArray = new JSONArray(listMeses);
            return convertBasicDto.getBasicDto(jsonArray);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fatal " + e.getMessage());
        }
    }

    public ResponseEntity<MesesEntity> addRegister(MesesEntity meses){
        try {
            mesesRepository.save(meses);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el mes" + e.getMessage());
        }
    }

    public ResponseEntity<MesesEntity> editRegister(Long id, MesesEntity meses){
        Optional<MesesEntity> mesesId = mesesRepository.findById(id);
        
        if(mesesId.isPresent()){
            mesesRepository.save(meses);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + meses);
        }
    }

    public ResponseEntity<MesesEntity> editStatus(Long id, MesesEntity meses){
        Optional<MesesEntity> mesesId = mesesRepository.findById(id);
        
        if(mesesId.isPresent()){
            MesesEntity mesesEntity = mesesId.get();
            mesesEntity.setStatus(meses.getStatus());
            mesesRepository.save(mesesEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + meses);
        }
    }
}
