package com.ceag.facturacion.Utils.DatosFactura;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DatosImpuesto {
    private String codImpuesto;
    private String codTipoFactor;
    private String codTasaCuota;
    private String base;
    private String importe;
    private Boolean isTrasladado;
    // private Double subTotal;
    // private Double descuento;
    // private Double total;
}
