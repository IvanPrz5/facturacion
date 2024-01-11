package com.ceag.facturacion.Service.Facturacion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Facturacion.ConceptoPrecargadoDto;
import com.ceag.facturacion.Entity.Facturacion.ConceptosPrecargadosEntity;
import com.ceag.facturacion.Repository.Facturacion.ConceptosPrecargadosRepository;
import com.ceag.facturacion.Utils.DatosFactura.DatosImpuesto;

@Service
public class ConceptosPrecargadosService {
    
    @Autowired
    ConceptosPrecargadosRepository conceptosPrecargadosRepository;

    public List<ConceptoPrecargadoDto> getConceptosPre() throws Exception{
        try {
            List<ConceptosPrecargadosEntity> conceptos = conceptosPrecargadosRepository.findAll();
            ConceptoPrecargadoDto conceptoPrecargadoDto = null;
            List<ConceptoPrecargadoDto> listConceptos = new ArrayList<>();
            DatosImpuesto datosImpuesto = null;
            List<DatosImpuesto> datosImpList = new ArrayList<>();

            for(int i=0; i<conceptos.size(); i++){
                conceptoPrecargadoDto = new ConceptoPrecargadoDto();
                datosImpuesto = new DatosImpuesto();

               /*  conceptoPrecargadoDto.setIdClaveProdServ(conceptos.get(i).getClaveProdServ() + ".- " + conceptos.get(i).getProdServ());
                // conceptoPrecargadoDto.setClaveProdServ(conceptos.get(0).getProdServ());
                conceptoPrecargadoDto.setIdClaveUnidad(conceptos.get(i).getClaveUnidad() + ".- " + conceptos.get(i).getUnidad());
                // conceptoPrecargadoDto.setUnidad(conceptos.get(0).getUnidad());
                conceptoPrecargadoDto.setDescripcion("");
                conceptoPrecargadoDto.setValorUnitario(conceptos.get(i).getValorUnitario());
                conceptoPrecargadoDto.setImporte(conceptos.get(i).getImporte());
                conceptoPrecargadoDto.setDescuento(conceptos.get(i).getDescuento()); */
                conceptoPrecargadoDto.setCantidad(conceptos.get(i).getCantidad());
                // conceptoPrecargadoDto.setIdObjetoImp(conceptos.get(i).getObjetoImp());

                /* datosImpuesto.setCodImpuesto(conceptos.get(i).getIdTrasladoPrecargado().getImpuesto());
                datosImpuesto.setCodTipoFactor(conceptos.get(i).getIdTrasladoPrecargado().getTipoFactor());
                datosImpuesto.setCodTasaCuota(conceptos.get(i).getIdTrasladoPrecargado().getTasaCuota());
                datosImpuesto.setBase(Double.parseDouble(conceptos.get(i).getIdTrasladoPrecargado().getBase()));
                datosImpuesto.setImporte(Double.parseDouble(conceptos.get(i).getIdTrasladoPrecargado().getImporte())); */

                datosImpList.add(datosImpuesto);

                conceptoPrecargadoDto.setDatosImpuesto(datosImpList);

                listConceptos.add(conceptoPrecargadoDto);
            }

            /* if(conceptos.get(0).getIdTrasladoPrecargado() != null){

            } */

            // https://github.com/f3l3p1n0/skullsage
            return listConceptos;
        } catch (Exception e) {
            throw new Exception("Error datos precargados" + e.getMessage());
        }
    }

    /* public ResponseEntity<List<ConceptosPrecargadosEntity>> getConceptosPrecargados() throws Exception{
        try {
            List<ConceptosPrecargadosEntity> conceptosPrecargados = conceptosPrecargadosRepository.findAll();
            return new ResponseEntity<>(conceptosPrecargados, HttpStatus.OK);
        } catch (Exception e) {
            throw new Exception("Error conceptos precargados " + e.getMessage());
        }
    } */
}
