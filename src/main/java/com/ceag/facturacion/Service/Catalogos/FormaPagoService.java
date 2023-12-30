package com.ceag.facturacion.Service.Catalogos;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Entity.Catalogos.FormaPagoEntity;
import com.ceag.facturacion.Repository.Catalogos.FormaPagoRepository;
import com.ceag.facturacion.Utils.Catalogos.ConvertBasicDto;

@Service
public class FormaPagoService {
    @Autowired
    FormaPagoRepository formaPagoRepository;

    public List<BasicDto> getRegisters(){
        try {
            List<FormaPagoEntity> listFormaPago = formaPagoRepository.findByStatus(true);
            ConvertBasicDto convertBasicDto = new ConvertBasicDto();
            JSONArray jsonArray = new JSONArray(listFormaPago);
            return convertBasicDto.getBasicDto(jsonArray);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fatal " + e.getMessage());
        }
    }

    public ResponseEntity<FormaPagoEntity> addRegister(FormaPagoEntity formaPagoEntity){
        try {
            formaPagoRepository.save(formaPagoEntity);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado la forma pago" + e.getMessage());
        }
    }

    public ResponseEntity<FormaPagoEntity> editRegister(Long id, FormaPagoEntity formaPago){
        Optional<FormaPagoEntity> formaPagoId = formaPagoRepository.findById(id);
        
        if(formaPagoId.isPresent()){
            formaPagoRepository.save(formaPago);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + formaPago);
        }
    }

    public ResponseEntity<FormaPagoEntity> editStatus(Long id, FormaPagoEntity formaPago){
        Optional<FormaPagoEntity> formaPagoId = formaPagoRepository.findById(id);
        
        if(formaPagoId.isPresent()){
            FormaPagoEntity formaPagoEntity = formaPagoId.get();
            formaPagoEntity.setStatus(formaPago.getStatus());
            formaPagoRepository.save(formaPagoEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + formaPago);
        }
    }
}
