package com.ceag.facturacion.Utils.DatosFactura;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DatosFactura {
    private DatosComprobante datosComprobante;
    // private DatosEmisor datosEmisor;
    private DatosReceptor datosReceptor;
    private List<DatosConcepto> datosConcepto;
}
