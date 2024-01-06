package com.ceag.facturacion.Dto.Xml;

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
