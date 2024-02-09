package com.ceag.facturacion.Dto.Empresas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpresasDto {
    private Long id;
    private String nombre;
    private String rfc;
    private String logo;
    private String codPostal;
}
