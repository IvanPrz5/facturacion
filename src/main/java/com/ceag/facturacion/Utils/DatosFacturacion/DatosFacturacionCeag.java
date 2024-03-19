package com.ceag.facturacion.Utils.DatosFacturacion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DatosFacturacionCeag {
    private String noCertificado  ;
    private String cerb64;// base 64
    private String keyb64;// base 64
    private String rutaKey ;
    private String contraseñaKey;

    private String nombreEmisor  ;
    private String rfc ;
    private String regimenFiscal ;
    private String codigoPostal;

    private String usuarioSw;
    private String contraseñaSw;
    private String urlSw;
    private String urlSwLogin;
    private String urlSwCancelar;
    private String urlSwJson ="https://services.test.sw.com.mx/cfdi33/issue/json/v4";

    public DatosFacturacionCeag(Boolean productivo){
        if(productivo){
            //  Productivo
            usuarioSw = "rogegarcia1420@gmail.com";
            contraseñaSw = "Qazxsw2.Sw";
            urlSw = "https://services.sw.com.mx/cfdi33/stamp/json/v4";
            urlSwLogin = "https://services.test.sw.com.mx/security/authenticate";
            urlSwCancelar = "https://services.sw.com.mx/cfdi33/cancel/csd";
        }else{  
            //  Pruebas
            usuarioSw = "cristianmartinez@ceag.com.mx";
            contraseñaSw = "Esteban5";
            urlSw = "https://services.test.sw.com.mx/cfdi33/stamp/json/v4";
            urlSwLogin = "https://services.test.sw.com.mx/security/authenticate";
            urlSwCancelar = "https://services.test.sw.com.mx/cfdi33/cancel/csd";
        }
}

}