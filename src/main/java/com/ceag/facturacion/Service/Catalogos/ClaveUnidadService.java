package com.ceag.facturacion.Service.Catalogos;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Catalogos.ClaveUnidadDto;
import com.ceag.facturacion.Entity.Catalogos.ClaveUnidadEntity;
import com.ceag.facturacion.Repository.Catalogos.ClaveUnidadRepository;
import com.ceag.facturacion.Utils.Catalogos.ConvertDto;

@Service
public class ClaveUnidadService {
    @Autowired
    ClaveUnidadRepository claveUnidadRepository;

    public List<ClaveUnidadDto> getByCodigoOrDescripcion(String codigo){
        try {
            List<ClaveUnidadEntity> listClaveUnidad = claveUnidadRepository.findByCodigoAndStatus(codigo, true);
            if(listClaveUnidad.isEmpty()){
                listClaveUnidad = claveUnidadRepository.findByNombreContaining(codigo);
            }
            ConvertDto convertBasicDto = new ConvertDto();
            JSONArray jsonArray = new JSONArray(listClaveUnidad);
            
            return convertBasicDto.getClaveUnidadDto(jsonArray);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fatal " + e.getMessage());
        }
    }

    public ResponseEntity<ClaveUnidadEntity> addRegister(ClaveUnidadEntity claveUnidad){
        try {
            claveUnidadRepository.save(claveUnidad);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado la clave unidad" + e.getMessage());
        }
    }

    public ResponseEntity<ClaveUnidadEntity> editRegister(Long id, ClaveUnidadEntity claveUnidad){
        Optional<ClaveUnidadEntity> claveUnidadId =  claveUnidadRepository.findById(id);
        
        if(claveUnidadId.isPresent()){
            claveUnidadRepository.save(claveUnidad);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + claveUnidad);
        }
    }

        public ResponseEntity<ClaveUnidadEntity> editStatus(Long id, ClaveUnidadEntity claveUnidad){
        Optional<ClaveUnidadEntity> claveUnidadId =  claveUnidadRepository.findById(id);
        
        if(claveUnidadId.isPresent()){
            ClaveUnidadEntity claveUnidadEntity = claveUnidadId.get();
            claveUnidadEntity.setStatus(claveUnidad.getStatus());
            claveUnidadRepository.save(claveUnidadEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + claveUnidad);
        }
    }
}
