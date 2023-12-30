package com.ceag.facturacion.Service.Catalogos;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Entity.Catalogos.ExportacionEntity;
import com.ceag.facturacion.Repository.Catalogos.ExportacionRepository;
import com.ceag.facturacion.Utils.Catalogos.ConvertBasicDto;

@Service
public class ExportacionService {
    @Autowired
    ExportacionRepository exportacionRepository;

    public List<BasicDto> getRegisters(){
        try {
            List<ExportacionEntity> listExportacion = exportacionRepository.findByStatus(true);
            ConvertBasicDto convertBasicDto = new ConvertBasicDto();
            JSONArray jsonArray = new JSONArray(listExportacion);
            return convertBasicDto.getBasicDto(jsonArray);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fatal " + e.getMessage());
        }
    }

    public ResponseEntity<ExportacionEntity> addRegister(ExportacionEntity exportacion){
        try {
            exportacionRepository.save(exportacion);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado la exportacion" + e.getMessage());
        }
    }

    public ResponseEntity<ExportacionEntity> editRegister(Long id, ExportacionEntity exportacion){
        Optional<ExportacionEntity> estadoId = exportacionRepository.findById(id);
        
        if(estadoId.isPresent()){
            exportacionRepository.save(exportacion);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + exportacion);
        }
    }

    public ResponseEntity<ExportacionEntity> editStatus(Long id, ExportacionEntity exportacion){
        Optional<ExportacionEntity> exportacionId = exportacionRepository.findById(id);
        
        if(exportacionId.isPresent()){
            ExportacionEntity exportacionEntity = exportacionId.get();
            exportacionEntity.setStatus(exportacion.getStatus());
            exportacionRepository.save(exportacionEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + exportacion);
        }
    }
}
