package com.ceag.facturacion.Dto.Catalogos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasicDto {
    private Long id;
    private String codigo;
    private String descripcion;
}
