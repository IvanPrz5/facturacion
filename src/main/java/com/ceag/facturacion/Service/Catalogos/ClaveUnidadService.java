package com.ceag.facturacion.Service.Catalogos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Entity.Catalogos.ClaveUnidadEntity;
import com.ceag.facturacion.Repository.Catalogos.ClaveUnidadRepository;

@Service
public class ClaveUnidadService {
    @Autowired
    ClaveUnidadRepository claveUnidadRepository;

    public ResponseEntity<List<BasicDto>> getByCodigoOrDescripcion(String codigo){
        try {
            List<ClaveUnidadEntity> listClaveUnidad = claveUnidadRepository.findByCodigoAndStatus(codigo, true);
            if(listClaveUnidad.isEmpty()){
                listClaveUnidad = claveUnidadRepository.findByNombreContaining(codigo);
            }
            List<BasicDto> lista = new ArrayList<>();
            for(int i=0; i<listClaveUnidad.size(); i++){
                BasicDto basicDto = new BasicDto();
                basicDto.setId(listClaveUnidad.get(i).getId());
                basicDto.setCodigo(listClaveUnidad.get(i).getCodigo());
                basicDto.setDescripcion(listClaveUnidad.get(i).getNombre());
                lista.add(basicDto);
            }
            
            return new ResponseEntity<>(lista, HttpStatus.OK);
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
