package com.ceag.facturacion.Service.Facturacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Entity.Facturacion.ComprobanteEntity;
import com.ceag.facturacion.Entity.Facturacion.LocalesEntity;
import com.ceag.facturacion.Repository.Facturacion.LocalesRepository;
import com.ceag.facturacion.Utils.DatosFactura.DatosFactura;

@Service
public class LocalesService {
    @Autowired
    LocalesRepository localesRepository;

    public ResponseEntity<Long> addLocale(byte[] xmlByte, ComprobanteEntity comprobanteEntity, DatosFactura datosFactura){
        try {
            for(int i=0; i<datosFactura.getDatosLocales().size(); i++){
                LocalesEntity localesEntity = new LocalesEntity();
                localesEntity.setImpuesto(datosFactura.getDatosLocales().get(i).getImpuesto());
                localesEntity.setTasaCuota(datosFactura.getDatosLocales().get(i).getTasaCuota());
                localesEntity.setImporte(datosFactura.getDatosLocales().get(i).getImporte().toString());
                localesEntity.setIsTrasladado(datosFactura.getDatosLocales().get(i).getIsTrasladado());
                localesEntity.setStatus(true);
                localesEntity.setIdComprobante(comprobanteEntity);
                localesRepository.save(localesEntity);
            }
            
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el locale" + e.getMessage());
        }
    }
}
