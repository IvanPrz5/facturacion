package com.ceag.facturacion.Service.Catalogos;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Catalogos.BasicDto;
import com.ceag.facturacion.Entity.Catalogos.TasaCuotaEntity;
import com.ceag.facturacion.Repository.Catalogos.TasaCuotaRepository;

@Service
public class TasaCuotaService {
    @Autowired
    TasaCuotaRepository tasaCuotaRepository;

    public ResponseEntity<List<BasicDto>> getByImpuestoAndFactor(String impuesto, String factor){
        try {
            List<TasaCuotaEntity> listTipoFactor = tasaCuotaRepository.findByImpuestoAndFactor(impuesto, factor);
            List<BasicDto> lista = new ArrayList<>();
            for(int i=0; i<listTipoFactor.size(); i++){
                BasicDto basicDto = new BasicDto();
                basicDto.setId(listTipoFactor.get(i).getId());
                basicDto.setCodigo("No Aplica");
                basicDto.setDescripcion(listTipoFactor.get(i).getValorMaximo().toString());
                lista.add(basicDto);
            }
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error en tipo factor " + e.getMessage());
        }
    }

    public ResponseEntity<List<BasicDto>> getByStatus(){
        try {
            List<TasaCuotaEntity> listTipoFactor = tasaCuotaRepository.findByStatus(true);
            List<BasicDto> lista = new ArrayList<>();
            for(int i=0; i<listTipoFactor.size(); i++){
                BasicDto basicDto = new BasicDto();
                basicDto.setId(listTipoFactor.get(i).getId());
                basicDto.setCodigo("No Aplica");
                basicDto.setDescripcion(listTipoFactor.get(i).getValorMaximo());
                lista.add(basicDto);
            }
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error en tipo factor " + e.getMessage());
        }
    }

    public ResponseEntity<TasaCuotaEntity> addRegister(TasaCuotaEntity tasaCuota){
        try {
            tasaCuotaRepository.save(tasaCuota);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado la tasa cuota" + e.getMessage());
        }
    }

    public ResponseEntity<TasaCuotaEntity> editRegister(Long id, TasaCuotaEntity tasaCuota){
        Optional<TasaCuotaEntity> tasaCuotaId = tasaCuotaRepository.findById(id);
        if(tasaCuotaId.isPresent()){
            tasaCuotaRepository.save(tasaCuota);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + tasaCuota);
        }
    }

    public ResponseEntity<TasaCuotaEntity> editStatus(Long id, TasaCuotaEntity tasaCuota){
        Optional<TasaCuotaEntity> tasaCuotaId = tasaCuotaRepository.findById(id);
        if(tasaCuotaId.isPresent()){
            TasaCuotaEntity tasaCuotaEntity = tasaCuotaId.get();
            tasaCuotaEntity.setStatus(tasaCuota.getStatus());
            tasaCuotaRepository.save(tasaCuotaEntity);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }else{
            throw new IllegalArgumentException("Error al editar " + tasaCuota);
        }
    }
}
