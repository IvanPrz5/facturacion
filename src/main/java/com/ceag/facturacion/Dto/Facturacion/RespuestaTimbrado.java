package com.ceag.facturacion.Dto.Facturacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RespuestaTimbrado {
    Integer status;
    String mensaje;
}
