package com.ceag.facturacion.Utils.DatosFactura;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DatosConcepto {
    private String idClaveProdServ;
    private String claveProdServ;
    private String cantidad;
    private String idClaveUnidad;
    private String unidad;
    private String descripcion;
    private Double valorUnitario;
    private Double importe;
    private Double descuento;
    private String objetoImp;
}
