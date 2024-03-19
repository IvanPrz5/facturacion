package com.ceag.facturacion.Service.Catalogos;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Entity.Catalogos.TipoComprobanteEntity;
import com.ceag.facturacion.Repository.Catalogos.TipoComprobanteRepository;
import com.ceag.facturacion.Utils.Catalogos.ConvertDto;

@Service
public class TipoComprobanteService { 
    @Autowired
    TipoComprobanteRepository tipoComprobanteRepository;

    public List<BasicDto> getRegisters(){
        try {
            List<TipoComprobanteEntity> listTipoComprobante = tipoComprobanteRepository.findByStatus(true);
            ConvertDto convertBasicDto = new ConvertDto();
            JSONArray jsonArray = new JSONArray(listTipoComprobante);
            return convertBasicDto.getBasicDto(jsonArray);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fatal " + e.getMessage());
        }
    }

    public ResponseEntity<TipoComprobanteEntity> addRegister(TipoComprobanteEntity tipoComprobante){
        try {
            tipoComprobanteRepository.save(tipoComprobante);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el tipo de comprobante" + e.getMessage());
        }
    }

    public ResponseEntity<TipoComprobanteEntity> editRegister(Long id, TipoComprobanteEntity tipoComprobante){
        Optional<TipoComprobanteEntity> tipoComprobanteId = tipoComprobanteRepository.findById(id);
        
        if(tipoComprobanteId.isPresent()){
            tipoComprobanteRepository.save(tipoComprobante);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + tipoComprobante);
        }
    }

    public ResponseEntity<TipoComprobanteEntity> editStatus(Long id, TipoComprobanteEntity tipoComprobante){
        Optional<TipoComprobanteEntity> tipoComprobanteId = tipoComprobanteRepository.findById(id);
        
        if(tipoComprobanteId.isPresent()){
            TipoComprobanteEntity tipoComprobanteEntity = tipoComprobanteId.get();
            tipoComprobanteEntity.setStatus(tipoComprobante.getStatus());
            tipoComprobanteRepository.save(tipoComprobanteEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + tipoComprobante);
        }
    }
}
