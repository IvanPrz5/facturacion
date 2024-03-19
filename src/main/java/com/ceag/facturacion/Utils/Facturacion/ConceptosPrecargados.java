package com.ceag.facturacion.Utils.Facturacion;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceag.facturacion.Dto.Facturacion.ConceptoPrecargadoDto;
import com.ceag.facturacion.Entity.Facturacion.ConceptosPrecargadosEntity;
import com.ceag.facturacion.Entity.Facturacion.ImpuestosPrecargadosEntity;
import com.ceag.facturacion.Repository.Facturacion.ImpuestosPrecargadosRepository;
import com.ceag.facturacion.Utils.DatosFactura.DatosImpuesto;

@Service
public class ConceptosPrecargados {
    @Autowired
    ImpuestosPrecargadosRepository trasladosPrecargadosRepository;

    public List<ConceptoPrecargadoDto> getConceptosPre(List<ConceptosPrecargadosEntity> conceptos,
            List<ImpuestosPrecargadosEntity> traslados, ImpuestosPrecargadosRepository trasladosPrecargadosRepository) {
        try {

            List<ConceptoPrecargadoDto> listConceptos = new ArrayList<>();
            ConceptoPrecargadoDto conceptoPrecargadoDto = null;
            DatosImpuesto datosImpuesto = null;

            for (int i = 0; i < conceptos.size(); i++) {
                conceptoPrecargadoDto = new ConceptoPrecargadoDto();

                conceptoPrecargadoDto.setId(conceptos.get(i).getId());
                conceptoPrecargadoDto.setIdClaveProdServ(
                        conceptos.get(i).getIdClaveProdServ() + ".- " + conceptos.get(i).getClaveProdServDesc());
                // conceptoPrecargadoDto.setClaveProdServ(conceptos.get(0).getProdServ());
                conceptoPrecargadoDto
                        .setIdClaveUnidad(conceptos.get(i).getIdClaveUnidad() + ".- " + conceptos.get(i).getUnidad());
                // conceptoPrecargadoDto.setUnidad(conceptos.get(0).getUnidad());
                conceptoPrecargadoDto.setDescripcion(conceptos.get(i).getDescripcion());
                conceptoPrecargadoDto.setValorUnitario(conceptos.get(i).getValorUnitario());
                conceptoPrecargadoDto.setImporte("0.00");
                conceptoPrecargadoDto.setDescuento(conceptos.get(i).getDescuento());
                conceptoPrecargadoDto.setCantidad(conceptos.get(i).getCantidad());
                conceptoPrecargadoDto.setIdObjetoImp(conceptos.get(i).getIdObjetoImp());
                conceptoPrecargadoDto.setFechaCreacion(conceptos.get(i).getFechaCreacion());
                conceptoPrecargadoDto.setPrecargado(true);

                List<DatosImpuesto> datosImpList = new ArrayList<>();
                for (int j = 0; j < conceptos.get(i).getDatosImpuestos().size(); j++) {
                    datosImpuesto = new DatosImpuesto();
                    datosImpuesto.setImpuesto(conceptos.get(i).getDatosImpuestos().get(j).getImpuesto());
                    datosImpuesto.setTipoFactor(conceptos.get(i).getDatosImpuestos().get(j).getTipoFactor());
                    datosImpuesto.setTasaCuota(conceptos.get(i).getDatosImpuestos().get(j).getTasaCuota());
                    datosImpuesto.setBase(Double.parseDouble(conceptos.get(i).getDatosImpuestos().get(j).getBase()));
                    datosImpuesto.setImporte(Double.parseDouble(conceptos.get(i).getDatosImpuestos().get(j).getImporteTraslado()));
                    datosImpuesto.setIsTrasladado(conceptos.get(i).getDatosImpuestos().get(j).getIsTrasladado());
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
