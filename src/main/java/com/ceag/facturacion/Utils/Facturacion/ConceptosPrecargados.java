package com.ceag.facturacion.Utils.Facturacion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Facturacion.ConceptoPrecargadoDto;
import com.ceag.facturacion.Entity.Facturacion.ConceptosPrecargadosEntity;
import com.ceag.facturacion.Entity.Facturacion.TrasladosPrecargadosEntity;
import com.ceag.facturacion.Repository.Facturacion.TrasladosPrecargadosRepository;
import com.ceag.facturacion.Utils.DatosFactura.DatosImpuesto;

@Service
public class ConceptosPrecargados {
    @Autowired
    TrasladosPrecargadosRepository trasladosPrecargadosRepository;

    public List<ConceptoPrecargadoDto> getConceptosPre(List<ConceptosPrecargadosEntity> conceptos,
            List<TrasladosPrecargadosEntity> traslados, TrasladosPrecargadosRepository trasladosPrecargadosRepository) {
        try {

            List<ConceptoPrecargadoDto> listConceptos = new ArrayList<>();
            ConceptoPrecargadoDto conceptoPrecargadoDto = null;
            DatosImpuesto datosImpuesto = null;

            for (int i = 0; i < conceptos.size(); i++) {
                conceptoPrecargadoDto = new ConceptoPrecargadoDto();

                conceptoPrecargadoDto.setIdClaveProdServ(
                        conceptos.get(i).getClaveProdServ() + ".- " + conceptos.get(i).getProdServ());
                // conceptoPrecargadoDto.setClaveProdServ(conceptos.get(0).getProdServ());
                conceptoPrecargadoDto
                        .setIdClaveUnidad(conceptos.get(i).getClaveUnidad() + ".- " + conceptos.get(i).getUnidad());
                // conceptoPrecargadoDto.setUnidad(conceptos.get(0).getUnidad());
                conceptoPrecargadoDto.setDescripcion("");
                conceptoPrecargadoDto.setValorUnitario(conceptos.get(i).getValorUnitario());
                conceptoPrecargadoDto.setImporte(conceptos.get(i).getImporte());
                conceptoPrecargadoDto.setDescuento(conceptos.get(i).getDescuento());
                conceptoPrecargadoDto.setCantidad(conceptos.get(i).getCantidad());
                conceptoPrecargadoDto.setIdObjetoImp(conceptos.get(i).getObjetoImp());

                traslados = trasladosPrecargadosRepository
                        .findByIdConceptoPrecargado(conceptos.get(i));
                List<DatosImpuesto> datosImpList = new ArrayList<>();
                for (int j = 0; j < traslados.size(); j++) {
                    datosImpuesto = new DatosImpuesto();
                    datosImpuesto.setCodImpuesto(traslados.get(j).getImpuesto());
                    datosImpuesto.setCodTipoFactor(traslados.get(j).getTipoFactor());
                    datosImpuesto.setCodTasaCuota(traslados.get(j).getTasaCuota());
                    datosImpuesto.setBase(Double.parseDouble(traslados.get(j).getBase()));
                    datosImpuesto.setImporte(Double.parseDouble(traslados.get(j).getImporteTraslado()));
                    datosImpList.add(datosImpuesto);
                    conceptoPrecargadoDto.setDatosImpuesto(datosImpList);
                }

                listConceptos.add(conceptoPrecargadoDto);
            }

            return listConceptos;
        } catch (Exception e) {
            throw new IllegalArgumentException("Error datos precargados" + e.getMessage());
        }
    }
}
