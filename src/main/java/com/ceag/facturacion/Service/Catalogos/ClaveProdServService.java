package com.ceag.facturacion.Service.Catalogos;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Entity.Catalogos.ClaveProdServEntity;
import com.ceag.facturacion.Repository.Catalogos.ClaveProdServRepository;
import com.ceag.facturacion.Utils.Catalogos.ConvertBasicDto;

@Service
public class ClaveProdServService {
    @Autowired
    ClaveProdServRepository claveProdServRepository;

    public List<BasicDto> getRegistersByCodigo(String codigo){
        try {
            List<ClaveProdServEntity> listClaveProdServ = claveProdServRepository.findByCodigoAndStatus(codigo, true);
            if(listClaveProdServ.isEmpty()){
                listClaveProdServ = claveProdServRepository.findByDescripcionContaining(codigo);
            }
            ConvertBasicDto convertBasicDto = new ConvertBasicDto();
            JSONArray jsonArray = new JSONArray(listClaveProdServ);
            return convertBasicDto.getBasicDto(jsonArray);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fatal " + e.getMessage());
        }
    }
    
    public ResponseEntity<ClaveProdServEntity> addRegister(ClaveProdServEntity claveProdServ){
        try {
            claveProdServRepository.save(claveProdServ);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado la clave prod" + e.getMessage());
        }
    }

    public ResponseEntity<ClaveProdServEntity> editRegister(Long id, ClaveProdServEntity claveProdServ){
        Optional<ClaveProdServEntity> claveProdServId =  claveProdServRepository.findById(id);
        
        if(claveProdServId.isPresent()){
            claveProdServRepository.save(claveProdServ);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + claveProdServ);
        }
    }

    public ResponseEntity<ClaveProdServEntity> editStatus(Long id, ClaveProdServEntity claveProdServ){
        Optional<ClaveProdServEntity> claveProdServId =  claveProdServRepository.findById(id);

        if(claveProdServId.isPresent()){
            ClaveProdServEntity claveProdServEntity = claveProdServId.get();
            claveProdServEntity.setStatus(claveProdServ.getStatus());
            claveProdServRepository.save(claveProdServEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al eliminar " + claveProdServ);
        }
    }
}
