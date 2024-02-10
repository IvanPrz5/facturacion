package com.ceag.facturacion.Dto.Facturacion;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReceptorDto {
    // Receptor
    private String nombre;
    private String rfc;
    private String domicilioFiscal;
    private String regimenFiscal;
    private String usoCfdi;
}
