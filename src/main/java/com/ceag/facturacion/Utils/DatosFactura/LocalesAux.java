package com.ceag.facturacion.Utils.DatosFactura;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocalesAux {
    private String tipoImpLocal;
    private String impuesto;
    private String tasaCuota;
    private String importe; 
}
