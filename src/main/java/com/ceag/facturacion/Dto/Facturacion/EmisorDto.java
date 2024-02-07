package com.ceag.facturacion.Dto.Facturacion;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmisorDto {
    // Emisor
    private String rfc;
    private String nombre;
    private String regimenFiscal;
}
