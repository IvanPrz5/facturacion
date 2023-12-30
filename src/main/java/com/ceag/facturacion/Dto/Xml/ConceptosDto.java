package com.ceag.facturacion.Dto.Xml;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConceptosDto {
    
    // Concepto
    private String claveProdServ;
    private Double cantidad;
    private String claveUnidad;
    private String unidad;
    private String descripcion;
    private Double valorUnitario;
    private Double importe;
    private String objetoImp;
}
