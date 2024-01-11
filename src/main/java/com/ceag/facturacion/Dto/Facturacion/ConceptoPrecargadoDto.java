package com.ceag.facturacion.Dto.Facturacion;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ceag.facturacion.Utils.DatosFactura.DatosImpuesto;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConceptoPrecargadoDto {
    private String idClaveProdServ;
    private String claveProdServ;
    private String cantidad;
    private String idClaveUnidad;
    private String unidad;
    private String descripcion;
    private Double valorUnitario;
    private Double importe;
    private Double descuento;
    private String idObjetoImp;

    private List<DatosImpuesto> datosImpuesto;
}
