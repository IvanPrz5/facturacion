package com.ceag.facturacion.Service.Catalogos;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Entity.Catalogos.MonedaEntity;
import com.ceag.facturacion.Repository.Catalogos.MonedaRepository;
import com.ceag.facturacion.Utils.Catalogos.ConvertBasicDto;

@Service
public class MonedaService {
    @Autowired
    MonedaRepository monedaRepository;

    public List<BasicDto> getRegisters(){
        try {
            List<MonedaEntity> listMoneda = monedaRepository.findByStatus(true);
            ConvertBasicDto convertBasicDto = new ConvertBasicDto();
            JSONArray jsonArray = new JSONArray(listMoneda);
            return convertBasicDto.getBasicDto(jsonArray);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fatal " + e.getMessage());
        }
    }

    public ResponseEntity<MonedaEntity> addRegister(MonedaEntity moneda){
        try {
            monedaRepository.save(moneda);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado la moneda" + e.getMessage());
        }
    }

    public ResponseEntity<MonedaEntity> editRegister(Long id, MonedaEntity moneda){
        Optional<MonedaEntity> monedaId = monedaRepository.findById(id);
        
        if(monedaId.isPresent()){
            monedaRepository.save(moneda);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + moneda);
        }
    }

    public ResponseEntity<MonedaEntity> editStatus(Long id, MonedaEntity moneda){
        Optional<MonedaEntity> monedaId = monedaRepository.findById(id);
        
        if(monedaId.isPresent()){
            MonedaEntity monedaEntity = monedaId.get();
            monedaEntity.setStatus(moneda.getStatus());
            monedaRepository.save(monedaEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + moneda);
        }
    }
}
