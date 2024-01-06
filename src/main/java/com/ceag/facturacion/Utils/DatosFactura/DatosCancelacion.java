package com.ceag.facturacion.Utils.DatosFactura;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DatosCancelacion {
    private String uuid;
    private String password;
    private String rfc;
    private String motivo;
    private String b64Cer;
    private String b64Key;
}
