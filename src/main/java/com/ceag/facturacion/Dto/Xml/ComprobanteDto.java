package com.ceag.facturacion.Dto.Xml;

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
     // Comprobante
    private Boolean isTimbrado;
    private String tipoComprobante;
    private String exportacion;
    private String metodoPago;
    private String formaPago;
    private String lugarExpedicion;
    private LocalDateTime fecha;
    private String moneda;
    private Double subTotal;
    private Double total;
    private Double descuento;
    // private String noCertificado;
    // private String sello;
    // private String certificado;
}
