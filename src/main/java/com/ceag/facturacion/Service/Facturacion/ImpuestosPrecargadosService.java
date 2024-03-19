package com.ceag.facturacion.Service.Facturacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Entity.Facturacion.ConceptosPrecargadosEntity;
import com.ceag.facturacion.Entity.Facturacion.ImpuestosPrecargadosEntity;
import com.ceag.facturacion.Repository.Facturacion.ImpuestosPrecargadosRepository;
import com.ceag.facturacion.Utils.DatosFactura.DatosPrecargados;

@Service
public class ImpuestosPrecargadosService {
    @Autowired
    ImpuestosPrecargadosRepository trasladosPrecargadosRepository;

    public ResponseEntity<List<ImpuestosPrecargadosEntity>> getTrasladosPrecargados() throws Exception{
        try {
            List<ImpuestosPrecargadosEntity> trasladosPrecargados = trasladosPrecargadosRepository.findAll();
            return new ResponseEntity<>(trasladosPrecargados, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception("Error Traslados Precargados" + e.getMessage());
        }
    }

    public ResponseEntity<Long> addImpuestoPrecargado(DatosPrecargados datosFactura, ConceptosPrecargadosEntity conceptoEntity){
        try {
            for(int j = 0; j < datosFactura.getDatosConcepto().getDatosImpuesto().size(); j++){
                ImpuestosPrecargadosEntity impuestosPrecargadosEntity = new ImpuestosPrecargadosEntity();
                impuestosPrecargadosEntity.setBase(datosFactura.getDatosConcepto().getDatosImpuesto().get(j).getBase().toString());
                impuestosPrecargadosEntity.setImpuesto(datosFactura.getDatosConcepto().getDatosImpuesto().get(j).getImpuesto());
                impuestosPrecargadosEntity.setTipoFactor(datosFactura.getDatosConcepto().getDatosImpuesto().get(j).getTipoFactor());
                impuestosPrecargadosEntity.setTasaCuota(datosFactura.getDatosConcepto().getDatosImpuesto().get(j).getTasaCuota());
                impuestosPrecargadosEntity.setImporteTraslado(datosFactura.getDatosConcepto().getDatosImpuesto().get(j).getImporte().toString());
                impuestosPrecargadosEntity.setIsTrasladado(datosFactura.getDatosConcepto().getDatosImpuesto().get(j).getIsTrasladado());
                // impuestosPrecargadosEntity.setStatus(true);
                impuestosPrecargadosEntity.setIdConceptoPrecargado(conceptoEntity);

                trasladosPrecargadosRepository.save(impuestosPrecargadosEntity);
            }
            return new ResponseEntity<>(null, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new IllegalArgumentException("No se ha registrado el traslado" + e.getMessage());
        }
    }
}
