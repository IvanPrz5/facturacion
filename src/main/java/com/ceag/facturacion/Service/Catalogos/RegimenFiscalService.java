package com.ceag.facturacion.Service.Catalogos;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Entity.Catalogos.RegimenFiscalEntity;
import com.ceag.facturacion.Repository.Catalogos.RegimenFiscalRepository;
import com.ceag.facturacion.Utils.Catalogos.ConvertDto;

@Service
public class RegimenFiscalService {
    @Autowired
    RegimenFiscalRepository regimenFiscalRepository;

    public List<RegimenFiscalEntity> getByCodigo(String cod){
        try {
            return regimenFiscalRepository.findByCodigo(cod);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fatal " + e.getMessage());
        }
    }

    public List<BasicDto> getRegisters(){
        try {
            List<RegimenFiscalEntity> listRegimenFiscal = regimenFiscalRepository.findByStatus(true);
            ConvertDto convertBasicDto = new ConvertDto();
            JSONArray jsonArray = new JSONArray(listRegimenFiscal);
            return convertBasicDto.getBasicDto(jsonArray);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fatal " + e.getMessage());
        }
    }

    public ResponseEntity<RegimenFiscalEntity> addRegister(RegimenFiscalEntity regimenFiscal){
        try {
            regimenFiscalRepository.save(regimenFiscal);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el regimen fiscal" + e.getMessage());
        }
    }

    public ResponseEntity<RegimenFiscalEntity> editRegister(Long id, RegimenFiscalEntity regimenFiscal){
        Optional<RegimenFiscalEntity> regimenFiscalId = regimenFiscalRepository.findById(id);
        
        if(regimenFiscalId.isPresent()){
            regimenFiscalRepository.save(regimenFiscal);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + regimenFiscal);
        }
    }

    public ResponseEntity<RegimenFiscalEntity> editStatus(Long id, RegimenFiscalEntity regimenFiscal){
        Optional<RegimenFiscalEntity> regimenFiscalId = regimenFiscalRepository.findById(id);
        
        if(regimenFiscalId.isPresent()){
            RegimenFiscalEntity regimenFiscalEntity = regimenFiscalId.get();
            regimenFiscalEntity.setStatus(regimenFiscal.getStatus());
            regimenFiscalRepository.save(regimenFiscalEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + regimenFiscal);
        }
    }
}
