package com.ceag.facturacion.Service.Facturacion;

import org.springframework.http.HttpStatus;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Xml.ReceptorDto;
import com.ceag.facturacion.Entity.Facturacion.ReceptorEntity;
import com.ceag.facturacion.Repository.Facturacion.ReceptorRepository;

@Service
public class ReceptorService {
    @Autowired
    ReceptorRepository receptorRepository;

    public ReceptorDto getReceptor(String rfc){
        try {
            Optional<ReceptorEntity> receptor = receptorRepository.findByRfc(rfc);
            ReceptorDto receptorDto = new ReceptorDto();
            receptorDto.setNombreReceptor(receptor.get().getNombre());
            receptorDto.setRfcReceptor(receptor.get().getRegimenFiscalReceptor());
            receptorDto.setDomicilioFiscalReceptor(receptor.get().getDomicilioFiscalReceptor());
            receptorDto.setRegimenFiscalReceptor(receptor.get().getRegimenFiscalReceptor());
            receptorDto.setUsoCfdiReceptor(receptor.get().getUsoCfdi());
            return receptorDto;
        } catch (Exception e) {
            throw new IllegalArgumentException("No se obtuvo el receptor " + e.getMessage());
        }
    }

    public ResponseEntity<Long> addReceptor(ReceptorEntity receptor){
        try {
            receptorRepository.save(receptor);
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el receptor" + e.getMessage());
        }
    }
}
