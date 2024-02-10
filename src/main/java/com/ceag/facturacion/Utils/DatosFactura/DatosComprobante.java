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
    private String id;
    private String idTipoComprobante;
    private String idExportacion;
    private String idFormaPago;
    private String idMetodoPago;
    private String lugarExpedicion;
    private Boolean isTimbrado;
    private String folio;
    private String serie;
}
