package com.ceag.facturacion.Utils.DatosFactura;

import java.util.List;

import com.ceag.facturacion.Dto.Facturacion.PagosDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DatosPPD {
    private String xml;
    private PagosDto pagosDto;
    private DatosComprobante datosComprobante;
    private List<DatosConcepto> datosConcepto;
    private DatosReceptor datosReceptor;
    private Long idEmpresa;
}
