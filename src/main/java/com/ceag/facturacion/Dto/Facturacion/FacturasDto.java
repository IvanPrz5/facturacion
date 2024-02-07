package com.ceag.facturacion.Dto.Facturacion;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FacturasDto {
    private List<ComprobanteDto> comprobanteDto;
    EmisorDto emisorDto;
    List<ConceptosDto> conceptosDto;
}
