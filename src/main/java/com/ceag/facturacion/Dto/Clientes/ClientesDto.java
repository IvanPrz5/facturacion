package com.ceag.facturacion.Dto.Clientes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientesDto {
    private Long id;
    private String nombre;
    private String rfc;
    private String domicilioFiscal;
    private Long idRegimenFiscal;
    // private Long idUso;
    private String regimenFiscal;
    // private String usoCfdi;
}
