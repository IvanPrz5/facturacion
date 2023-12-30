package com.ceag.facturacion.Service.Catalogos;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Entity.Catalogos.MetodoPagoEntity;
import com.ceag.facturacion.Repository.Catalogos.MetodoPagoRepository;
import com.ceag.facturacion.Utils.Catalogos.ConvertBasicDto;

@Service
public class MetodoPagoService {
    @Autowired
    MetodoPagoRepository metodoPagoRepository;

    public List<BasicDto> getRegisters(){
        try {
            List<MetodoPagoEntity> listMetodoPago = metodoPagoRepository.findByStatus(true);
            ConvertBasicDto convertBasicDto = new ConvertBasicDto();
            JSONArray jsonArray = new JSONArray(listMetodoPago);
            return convertBasicDto.getBasicDto(jsonArray);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fatal " + e.getMessage());
        }
    }

    public ResponseEntity<MetodoPagoEntity> addRegister(MetodoPagoEntity metodoPago){
        try {
            metodoPagoRepository.save(metodoPago);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el metodo pago" + e.getMessage());
        }
    }

    public ResponseEntity<MetodoPagoEntity> editRegister(Long id, MetodoPagoEntity metodoPago){
        Optional<MetodoPagoEntity> metodoPagoId = metodoPagoRepository.findById(id);
        
        if(metodoPagoId.isPresent()){
            metodoPagoRepository.save(metodoPago);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + metodoPago);
        }
    }

    public ResponseEntity<MetodoPagoEntity> editStatus(Long id, MetodoPagoEntity metodoPago){
        Optional<MetodoPagoEntity> metodoPagoId = metodoPagoRepository.findById(id);
        
        if(metodoPagoId.isPresent()){
            MetodoPagoEntity metodoPagoEntity = metodoPagoId.get();
            metodoPagoEntity.setStatus(metodoPago.getStatus());
            metodoPagoRepository.save(metodoPagoEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + metodoPago);
        }
    }
}
