package com.ceag.facturacion.Service.Catalogos;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Entity.Catalogos.ImpuestoEntity;
import com.ceag.facturacion.Repository.Catalogos.ImpuestoRepository;
import com.ceag.facturacion.Utils.Catalogos.ConvertBasicDto;

@Service
public class ImpuestoService{
    @Autowired
    ImpuestoRepository impuestoRepository;

    public List<BasicDto> getRegisters(){
        try {
            List<ImpuestoEntity> listImpuesto = impuestoRepository.findByStatus(true);
            ConvertBasicDto convertBasicDto = new ConvertBasicDto();
            JSONArray jsonArray = new JSONArray(listImpuesto);
            return convertBasicDto.getBasicDto(jsonArray);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fatal " + e.getMessage());
        }
    }

    public ResponseEntity<ImpuestoEntity> addRegister(ImpuestoEntity impuesto){
        try {
            impuestoRepository.save(impuesto);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el impuesto" + e.getMessage());
        }
    }

    public ResponseEntity<ImpuestoEntity> editRegister(Long id, ImpuestoEntity impuesto){
        Optional<ImpuestoEntity> impuestoId = impuestoRepository.findById(id);
        
        if(impuestoId.isPresent()){
            impuestoRepository.save(impuesto);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + impuesto);
        }
    }

    public ResponseEntity<ImpuestoEntity> editStatus(Long id, ImpuestoEntity impuesto){
        Optional<ImpuestoEntity> impuestoId = impuestoRepository.findById(id);
        
        if(impuestoId.isPresent()){
            ImpuestoEntity impuestoEntity = impuestoId.get();
            impuestoEntity.setStatus(impuesto.getStatus());
            impuestoRepository.save(impuestoEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + impuesto);
        }
    }
}
