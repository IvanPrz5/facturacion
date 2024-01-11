package com.ceag.facturacion.Utils.DatosFactura;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DatosReceptor{
    private String nombre;
    private String rfc;
    private String domicilioFiscal;
    private String regimenFiscal;
    private String usoCfdi;
    // private Double subTotal;
    // private Double descuento;
    // private Double total;
}