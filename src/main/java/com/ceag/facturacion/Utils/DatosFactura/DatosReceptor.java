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
    private String idCodigoPostal;
    private String codRegimenFiscal;
    private String codUsoCfdi;
    // private Double subTotal;
    // private Double descuento;
    // private Double total;
}