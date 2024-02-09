package com.ceag.facturacion.Utils.DatosFactura;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ConceptoAux {
    private String Cantidad;
    private String ClaveUnidad;
    private String Unidad;
    private String ClaveProdServ;
    private String ObjetoImp;
    private String Descripcion;
    private String ValorUnitario;
    private String Descuento;
    private String Importe;
}