package com.ceag.facturacion.Entity.Empresas;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Empresas")
public class EmpresasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 50)
    @NotNull
    private String nombre;

    @Column(length = 50)
    @NotNull
    private String rfc;
    
    @Column(length = 50)
    @NotNull
    private String curp;

    @Column(length = 20)
    @NotNull
    private String codPostal;

    @Column(length = 50)
    @NotNull
    private String numCertificado;
    
    @Column(length = 200)
    @NotNull
    private String usuarioPac;
    
    @Column(length = 100)
    @NotNull
    private String contraseñaPac;

    @Column(length = 50)
    @NotNull
    private String regimenFiscal;

    @Column(length = 50)
    @NotNull
    private String fisica;

    @Column(length = 20)
    @NotNull
    private String passKey;

    @Column(length = 3000)
    @NotNull
    private String cerB64;

    @Column(length = 3000)
    @NotNull
    private String routeCerB64;
    
    @Column(length = 3000)
    @NotNull
    private String keyB64;

    @Column(length = 250)
    @NotNull
    private String logo;

    @Column
    @NotNull
    private Boolean status;
}
