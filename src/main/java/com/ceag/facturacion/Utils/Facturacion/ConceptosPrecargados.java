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

                conceptoPrecargadoDto.setIdClaveProdServ(
                        conceptos.get(i).getIdClaveProdServ().getCodigo() + ".- " + conceptos.get(i).getIdClaveProdServ().getDescripcion());
                // conceptoPrecargadoDto.setClaveProdServ(conceptos.get(0).getProdServ());
                conceptoPrecargadoDto
                        .setIdClaveUnidad(conceptos.get(i).getIdClaveUnidad().getCodigo() + ".- " + conceptos.get(i).getIdClaveUnidad().getNombre());
                // conceptoPrecargadoDto.setUnidad(conceptos.get(0).getUnidad());
                conceptoPrecargadoDto.setDescripcion(conceptos.get(i).getDescripcion());
                conceptoPrecargadoDto.setValorUnitario(conceptos.get(i).getValorUnitario());
                conceptoPrecargadoDto.setImporte("0.00");
                conceptoPrecargadoDto.setDescuento(conceptos.get(i).getDescuento());
                conceptoPrecargadoDto.setCantidad(conceptos.get(i).getCantidad());
                conceptoPrecargadoDto.setIdObjetoImp(conceptos.get(i).getIdObjetoImp().getCodigo());

                traslados = trasladosPrecargadosRepository
                        .findByIdConceptoPrecargado(conceptos.get(i));
                List<DatosImpuesto> datosImpList = new ArrayList<>();
                for (int j = 0; j < traslados.size(); j++) {
                    datosImpuesto = new DatosImpuesto();
                    datosImpuesto.setImpuesto(traslados.get(j).getIdImpuesto().getCodigo());
                    datosImpuesto.setTipoFactor(traslados.get(j).getIdTipoFactor().getCodigo());
                    datosImpuesto.setTasaCuota(traslados.get(j).getIdTasaCuota().getValorMaximo());
                    datosImpuesto.setBase(Double.parseDouble(traslados.get(j).getBase()));
                    datosImpuesto.setImporte(Double.parseDouble(traslados.get(j).getImporteTraslado()));
                    datosImpuesto.setIsTrasladado(traslados.get(j).getIsTrasladado());
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
