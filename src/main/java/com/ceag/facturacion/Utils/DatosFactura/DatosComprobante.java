package com.ceag.facturacion.Utils.DatosFactura;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DatosComprobante {
    private String idTipoComprobante;
    private String idExportacion;
    private String idFormaPago;
    private String idMetodoPago;
}
