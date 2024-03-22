package com.ceag.facturacion.Utils.DatosFactura;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DatosLocales {
    private String impuesto;
    private String tasaCuota;
    private Double importe;
    private Boolean isTrasladado;
    private Boolean isEstatal;
    // private Double subTotal;
    // private Double descuento;
    // private Double total;
}
