package com.ceag.facturacion.Dto.Facturacion;

import java.time.LocalDateTime;

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
    private String idExportacion;
    private String idTipoComprobante;
    private String idMetodoPago;
    private String idFormaPago;
    private String lugarExpedicion;
    private String subTotal;
    private String descuento;
    private String total;
    private String uuid;
    private Boolean isTimbrado;
    private Boolean isCancelado;
    private String folio;
    private String serie;
    private LocalDateTime fecha;
    // private List<Conceptos> emisorDto;
}
