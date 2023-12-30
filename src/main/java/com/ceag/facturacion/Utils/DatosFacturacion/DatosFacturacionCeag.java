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
            rutaKey = "";
            cerb64 = "";
            keyb64 = "";
            noCertificado ="";
            contraseñaKey = "";

            nombreEmisor = "";
            rfc = "";
            codigoPostal="";
            regimenFiscal = "";

            usuarioSw = "";
            contraseñaSw = "";
            urlSw = "https://services.sw.com.mx/cfdi33/stamp/v4";
            urlSwLogin = "https://services.test.sw.com.mx/security/authenticate";
            urlSwCancelar = "https://services.sw.com.mx/cfdi33/cancel/csd";
        }else{  
            //  Pruebas
            rutaKey = "cfdi/certificados/CSD_Sucursal_1_KIJ0906199R1_20230518_062818.key";
            cerb64 = "MIIFvDCCA6SgAwIBAgIUMzAwMDEwMDAwMDA1MDAwMDM0MzcwDQYJKoZIhvcNAQELBQAwggErMQ8wDQYDVQQDDAZBQyBVQVQxLjAsBgNVBAoMJVNFUlZJQ0lPIERFIEFETUlOSVNUUkFDSU9OIFRSSUJVVEFSSUExGjAYBgNVBAsMEVNBVC1JRVMgQXV0aG9yaXR5MSgwJgYJKoZIhvcNAQkBFhlvc2Nhci5tYXJ0aW5lekBzYXQuZ29iLm14MR0wGwYDVQQJDBQzcmEgY2VycmFkYSBkZSBjYWxpejEOMAwGA1UEEQwFMDYzNzAxCzAJBgNVBAYTAk1YMRkwFwYDVQQIDBBDSVVEQUQgREUgTUVYSUNPMREwDwYDVQQHDAhDT1lPQUNBTjERMA8GA1UELRMIMi41LjQuNDUxJTAjBgkqhkiG9w0BCQITFnJlc3BvbnNhYmxlOiBBQ0RNQS1TQVQwHhcNMjMwNTE4MTI1MjUzWhcNMjcwNTE4MTI1MjUzWjCB4zErMCkGA1UEAxMiS0VSTkVMIElORFVTVElBIEpVR1VFVEVSQSBTQSBERSBDVjErMCkGA1UEKRMiS0VSTkVMIElORFVTVElBIEpVR1VFVEVSQSBTQSBERSBDVjErMCkGA1UEChMiS0VSTkVMIElORFVTVElBIEpVR1VFVEVSQSBTQSBERSBDVjElMCMGA1UELRMcS0lKMDkwNjE5OVIxIC8gVkFEQTgwMDkyN0RKMzEeMBwGA1UEBRMVIC8gVkFEQTgwMDkyN0hTUlNSTDA1MRMwEQYDVQQLEwpTdWN1cnNhbCAxMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgC0g7Rt021MxwbI8iwEdel1/gWBbC0lxJWcii66efHrjzAt5wwYTDrSKtQfpfa2KqCKH7y9o2fQYk4AmE9m8/wJXPRY+T47FDKmyD87/gz3tL88OEIPSpz+UYk7RGxRdwkC4WcQ0w0wrMLCwe80+O3YCajOxVbn9tWSeafUqslBdD7rxyiiLJmjxm4QYMiR/Hmi5+B5IDqnYNLpHECGu9nCGoqgR4uTKbuLBzWKqd/iPiDHzBu57uSAakRHkwfbHqMXNVDcsni+RVD52iRI/Xnz8ORkId0yDuhZnDtPkv/alxOo72Kcc6gzNIcut7tk0Jdm2vPYxXbP5vAqntylguwIDAQABox0wGzAMBgNVHRMBAf8EAjAAMAsGA1UdDwQEAwIGwDANBgkqhkiG9w0BAQsFAAOCAgEAGWv7VajQAbBj+dcj0oD6dqwCogQoj86HRPA4wT/WpeO+WPPhZIXBkw6q6l2oTS3CthsjbzgCTOMGIpEDCgtvSOJ3drU7eA6ok7FUEyvWuZULD2Hzdb8VbqUlu9L5SCLGxvg36C16iHLcTLOPKXfhhGV+JPc9NUkapeQsIkklvw9kLiQUcgij652BDKuKoxmp6zldihDQww6cQ3WxkNgjbE8hPxa7vMRJkp9Sd3cI0engKn+SYnO+CDOpDu3ySPUcn2aAI6vUx6e/Ernz/ooGh9YqNZxKm7d1PoV3x92F29xY9EMdGFod1AqkpJL454Seo2io45kLGh0wYepMI2/TXB1zTaD6GBOSgKH5r2M2PdrL8FlXyQBF7UBQGXpDB9Drj0Ru0wnoGhYDGz1qiBPQcz636IKC75wJ81bhkFUfMLiGUW0W7LuZKtsp/a+QHEV2httUm5lxZ+LmOK94rBnEJIVJb/nK9Tdy9eEJvS4jTwFbAb5FVnD5TeIII3+6h336AlxqL+bhgXECVLN6K11LrMQOCLnRz9ZuOoA+fueZ3vSIbmpuVDZGducnsWX+uArE8yAEJkrI2v759tO8K/ONVB5sJZgvFnqSswWgTsSIfVCx1uTXIQmv6Crb86xwrTSPo0WrhTQnqv4P+bCyICdBWhXsoBp9EL3ltHDc3kg9PB4=";
            keyb64 = "MIIFDjBABgkqhkiG9w0BBQ0wMzAbBgkqhkiG9w0BBQwwDgQIAgEAAoIBAQACAggAMBQGCCqGSIb3DQMHBAgwggS8AgEAMASCBMh4EHl7aNSCaMDA1VlRoXCZ5UUmqErAbucRFLOMmsAaFLPuyjxaYpkdhubp9j1s2k62DYKJxySuWO4DsXFSnCjXxLy62CVX+cosp++5+48dVmJzZ+lUS8rsqjeMVUApRdqNXSUax8CZjQ3zlLxe69J9N1CduHkJxRfx3ykJSKkFqyVEzUv5spIQXvc+eOQMw2u5HWvlAnhm67yEtdNtPw5DdvumSRNc/Kb5R2RW6sjczUmU1HWXWnv0DclguvFChcBSyw303tr0ANzkltuJSdy12TAAf8yE8g0+ETEqBhhRkywLNn0pLEWAxJ6hIRY24s0YggS8FNZEYJZRk7CI8QhJvdKD4qkspPPTIx6JC1aBiNTp86+mcG7XoW/z211lSMcGNv1sdyMSrY/YXJiQSRaiIhI9hnz9HDjYq2mITWhFfR4jcLMrMopQipi1xpbYsk027LHp5xn+l2XK5GvGSC/fHl2x+AG0ZJtcdCggGwIwn9FlJ3hvwyMLBTQ8bik0tg6eaU+rfgGkP7fFXEiB6Y7KXASq1kshlMzQbSDUg8tjvJ4HJvc6WPuJArUPohTePPi1ZGW/eFTP3VIa8333hX16nESsE8xwFQEUyhWowXJ/ISBVXJknFzNzzA17M3TnaHBZ4SmYml2kcGqNCtt9M0HOzqHVVZMvb7OelB10/ijGfPPViUbKr1qSkjPLgJ+4Vh2B+M4pybrumEwe6illzT1wIw+9dXkHfV522Ak9O+hchO20c/3EaTHdEzNC7CteURbV3kDo+7baBSsvlVtBVhsbJJgnDSe+mE7/0W02eEnAvMM9xHFcK40AAQOHFdSqIbqzxKeIGVydnH1dUpMAvQupj7KbCQSWWBOyPKGJVTqSOTzCE4TezGSdcCkQyVDM/ZYq8M9pqAPKAB5WCEWx6C+PTBLXPZePItQ5j6nuCLakrr5PTqfmhtUUg4msLJ6MYlcSW8PYQL9+VrzcXY3Nr2bSpTqFHi1vt8E36sBbkz0pDIQqcZOnOaM0xoF/f98XWphd1Q5HsM9xQgBon4nrQ2c/IPbiqoH95O7+KlspMhaHSpDNrxCKWoYOQEZx9puiKksppJY9yqtyJog3z3o4VLtu7PzP7YYr5DdxK1LKvucVc+JHor7WPeYOi/z7JzmhCPC60A1Kf8pWlebmzAWTqZFyXw1+20YvKUrZBL8sPekOo+9d3Sd7GyEV9oP9zNjZt7ABCMpYBcdC7E82P5VWexKKwRhaUS/qQ4zncS8baGYxKxNm9AeEgzWI48lebQ/LBkGj+YnEVdv8otMa1M9Yy3BEHqhZkvvEaFNTkBsunQaoebNbPztX8sZO6Y2SQDllLAmulZN0Mk7d60zn9zH7dvs2nx9I2ySXCuZdyqiIfI6+gnn9R+Osqgk7zbWM70oDLRufPRg2bHsyUYmYaZPxAKC4y/mLLvzO/oL9VKzc8hTyfgNf9OweRV0hw1Wd1EHKWPR0Y/dihuZBMEQJeIqn1xyTtmM7lvYCfQiEOXenYo3zW5l8Tz/f+PS8FhWAQH5rJhzp0Sz/Tt3LmN3l+pl8Z6ISyu16YsjAUN4Y1fCMgZLaLnqntRZk/fmeL8cCNpK3xQUJut3Kwn6csK97nx7gNIXYxOtGKpTx1Co=";
            noCertificado ="30001000000500003437";
            contraseñaKey = "12345678a";

            nombreEmisor = "KERNEL INDUSTIA JUGUETERA";
            rfc = "KIJ0906199R1";
            codigoPostal="68000";
            regimenFiscal = "622";

            usuarioSw = "cristianmartinez@ceag.com.mx";
            contraseñaSw = "admin123";
            urlSw = "https://services.test.sw.com.mx/cfdi33/stamp/v4";
            urlSwLogin = "https://services.test.sw.com.mx/security/authenticate";
            urlSwCancelar = "https://services.test.sw.com.mx/cfdi33/cancel/csd";
        }
}

}