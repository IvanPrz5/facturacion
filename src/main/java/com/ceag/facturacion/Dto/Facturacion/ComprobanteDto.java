package com.ceag.facturacion.Dto.Facturacion;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComprobanteDto {
    private String id;
    private String tipoComprobante;
    private String metodoPago;
    private String formaPago;
    private String lugarExpedicion;
    private String subTotal;
    private String descuento;
    private String total;
    private String uuid;
    private Boolean isTimbrado;
    // private List<Conceptos> emisorDto;
}
