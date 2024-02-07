package com.ceag.facturacion.Dto.Facturacion;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class XmlDto {
    private ComprobanteDto comprobanteDto;
    private ConceptosDto conceptosDto;
    private EmisorDto emisorDto;
    private ImpuestosDto impuestosDto;
    private ReceptorDto receptorDto;
    private TimbreFiscalDto timbreFiscalDigitalDto;
    private TrasladoDto trasladoDto;
}
