package com.ceag.facturacion.Service.Facturacion;

import org.springframework.http.HttpStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.ceag.facturacion.Entity.Facturacion.ConceptoEntity;
import com.ceag.facturacion.Entity.Facturacion.TrasladoEntity;
import com.ceag.facturacion.Repository.Facturacion.TrasladoRepository;
import com.ceag.facturacion.Utils.DatosFactura.DatosFactura;

@Service
public class TrasladoService {
    @Autowired
    TrasladoRepository trasladoRepository;

    public ResponseEntity<Long> addTraslado(DatosFactura datosFactura, int h, ConceptoEntity conceptoEntity){
        try {
            for(int j = 0; j < datosFactura.getDatosConcepto().get(h).getDatosImpuesto().size(); j++){
                TrasladoEntity trasladoEntity = new TrasladoEntity();
                trasladoEntity.setBase(datosFactura.getDatosConcepto().get(h).getDatosImpuesto().get(j).getBase().toString());
                trasladoEntity.setImpuesto(datosFactura.getDatosConcepto().get(h).getDatosImpuesto().get(j).getImpuesto());
                trasladoEntity.setTipoFactor(datosFactura.getDatosConcepto().get(h).getDatosImpuesto().get(j).getTipoFactor());
                trasladoEntity.setTasaCuota(datosFactura.getDatosConcepto().get(h).getDatosImpuesto().get(j).getTasaCuota());
                trasladoEntity.setImporte(datosFactura.getDatosConcepto().get(h).getDatosImpuesto().get(j).getImporte().toString());
                trasladoEntity.setIsTrasladado(datosFactura.getDatosConcepto().get(h).getDatosImpuesto().get(j).getIsTrasladado());
                trasladoEntity.setStatus(true);
                trasladoEntity.setIdConcepto(conceptoEntity);
                
                trasladoRepository.save(trasladoEntity);
            }

            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el traslado" + e.getMessage());
        }
    }
}
