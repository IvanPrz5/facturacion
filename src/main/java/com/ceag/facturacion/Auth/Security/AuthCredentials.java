package com.ceag.facturacion.Auth.Security;

import lombok.Data;

@Data
public class AuthCredentials {
    private String email;
    private String password;
}
