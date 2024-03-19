package com.ceag.facturacion.Utils.DatosFactura;

import java.util.Collection;
import java.util.List;

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
    private String datosImpuesto;
    private String datosTasaCuota;
    private String datosTipoFactor;
    private String datosImporte;
    private String datosBase;
    private String tipoImpuesto;
}