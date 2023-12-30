package com.ceag.facturacion.Dto.Xml;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TimbreFiscalDto {
    private String uuid;
    private LocalDateTime fechaTimbrado;
    private LocalDateTime rfcProvCer;
    private String selloCfdi;
    private String noCertificadoSat;
    private String selloSat;
}
