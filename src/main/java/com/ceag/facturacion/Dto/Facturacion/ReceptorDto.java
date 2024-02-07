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
    private String nombreReceptor;
    private String rfcReceptor;
    private String domicilioFiscalReceptor;
    private String regimenFiscalReceptor;
    private String usoCfdiReceptor;
}
