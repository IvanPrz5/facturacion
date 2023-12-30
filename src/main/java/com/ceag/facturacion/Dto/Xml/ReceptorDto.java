package com.ceag.facturacion.Dto.Xml;

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
    private String rfcReceptor;
    private String nombreReceptor;
    private String domicilioFiscalReceptor;
    private String regimenFiscalReceptor;
    private String usoCfdiReceptor;
}
