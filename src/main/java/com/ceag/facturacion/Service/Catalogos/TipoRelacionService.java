package com.ceag.facturacion.Service.Catalogos;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Entity.Catalogos.TipoRelacionEntity;
import com.ceag.facturacion.Repository.Catalogos.TipoRelacionRepository;
import com.ceag.facturacion.Utils.Catalogos.ConvertDto;

@Service
public class TipoRelacionService {
    @Autowired
    TipoRelacionRepository tipoRelacionRepository;

    public List<BasicDto> getRegisters(){
        try {
            List<TipoRelacionEntity> listTipoRelacion = tipoRelacionRepository.findByStatus(true);
            ConvertDto convertBasicDto = new ConvertDto();
            JSONArray jsonArray = new JSONArray(listTipoRelacion);
            return convertBasicDto.getBasicDto(jsonArray);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fatal " + e.getMessage());
        }
    }

    public ResponseEntity<TipoRelacionEntity> addRegister(TipoRelacionEntity tipoRelacion){
        try {
            tipoRelacionRepository.save(tipoRelacion);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el tipo relacion" + e.getMessage());
        }
    }

    public ResponseEntity<TipoRelacionEntity> editRegister(Long id, TipoRelacionEntity tipoRelacion){
        Optional<TipoRelacionEntity> tipoRelacionId = tipoRelacionRepository.findById(id);

        if(tipoRelacionId.isPresent()){
            tipoRelacionRepository.save(tipoRelacion);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + tipoRelacion);
        }
    }

    public ResponseEntity<TipoRelacionEntity> editStatus(Long id, TipoRelacionEntity tipoRelacion){
        Optional<TipoRelacionEntity> tipoRelacionId = tipoRelacionRepository.findById(id);

        if(tipoRelacionId.isPresent()){
            TipoRelacionEntity tipoRelacionEntity = tipoRelacionId.get();
            tipoRelacionEntity.setStatus(tipoRelacion.getStatus());
            tipoRelacionRepository.save(tipoRelacionEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + tipoRelacion);
        }
    }
}
