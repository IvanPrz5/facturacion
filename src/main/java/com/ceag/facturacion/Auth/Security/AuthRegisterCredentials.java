package com.ceag.facturacion.Auth.Security;

import lombok.Data;

@Data
public class AuthRegisterCredentials {
    private String nombre;
    private String apPaterno;
    private Long telefono;
    private String email;
    private String password;
}