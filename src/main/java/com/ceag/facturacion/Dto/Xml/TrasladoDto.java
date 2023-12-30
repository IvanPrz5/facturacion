package com.ceag.facturacion.Dto.Xml;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrasladoDto {
    private String base;
    private String impuesto;
    private String tipoFactor;
    private String tasaCuota;
    private String importe;
}
