package com.ceag.facturacion.Service.Catalogos;

import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Entity.Catalogos.UsoCFDIEntity;
import com.ceag.facturacion.Repository.Catalogos.UsoCFDIRepository;
import com.ceag.facturacion.Utils.Catalogos.ConvertBasicDto;

@Service
public class UsoCFDIService {
    @Autowired
    UsoCFDIRepository usoCfdiRepository;

    public List<BasicDto> getRegisters(){
        try {
            List<UsoCFDIEntity> listUsoCFDI = usoCfdiRepository.findByStatus(true);
            ConvertBasicDto convertBasicDto = new ConvertBasicDto();
            JSONArray jsonArray = new JSONArray(listUsoCFDI);
            return convertBasicDto.getBasicDto(jsonArray);
        } catch (Exception e) {
            throw new IllegalArgumentException("Fatal " + e.getMessage());
        }
    }

    public ResponseEntity<UsoCFDIEntity> addRegister(UsoCFDIEntity usoCfdi){
        try {
            usoCfdiRepository.save(usoCfdi);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el uso cfdi" + e.getMessage());
        }
    }

    public ResponseEntity<UsoCFDIEntity> editRegister(Long id, UsoCFDIEntity usoCfdi){
        Optional<UsoCFDIEntity> usoCfdiId = usoCfdiRepository.findById(id);

        if(usoCfdiId.isPresent()){
            usoCfdiRepository.save(usoCfdi);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + usoCfdi);
        }
    }

    public ResponseEntity<UsoCFDIEntity> editStatus(Long id, UsoCFDIEntity usoCfdi){
        Optional<UsoCFDIEntity> usoCfdiId = usoCfdiRepository.findById(id);

        if(usoCfdiId.isPresent()){
            UsoCFDIEntity usoCfdiEntity = usoCfdiId.get();
            usoCfdiEntity.setStatus(usoCfdi.getStatus());
            usoCfdiRepository.save(usoCfdiEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + usoCfdi);
        }
    }
}
