package com.ceag.facturacion.Dto.Facturacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PagosDto {
    private Long folioPago;
    private String usuario;
    private Long folioConvenio;
    private String uuid;
    private Long numParcialidad;
    private String formaPago;
    private Double impSaldoAnt;
    private Double impPagado;
    private Double impSaldoInsoluto;
}
