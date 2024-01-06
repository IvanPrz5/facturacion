package com.ceag.facturacion.Entity.Facturacion;

import java.time.LocalDateTime;

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
@Table(name="Comprobante")
public class ComprobanteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    @NotNull
    private String tipoComprobante;

    @Column(length = 50)
    @NotNull
    private String exportacion;

    @Column(length = 50)
    @NotNull
    private String metodoPago;

    @Column(length = 50)
    @NotNull
    private String formaPago;

    @Column(length = 50)
    @NotNull
    private String lugarExpedicion;

    @Column(length = 50)
    @NotNull
    private LocalDateTime fecha;

    @Column(length = 50)
    @NotNull
    private String moneda;

    @Column(length = 100)
    @NotNull
    private Double subTotal;

    @Column(length = 100)
    @NotNull
    private Double total;

    @Column(length = 100)
    @NotNull
    private Double descuento;

    /* @Column(length = 50)
    @NotNull
    private String noCertificado;

    @Column(length = 500)
    @NotNull
    private String sello;

    @Column(length = 2300)
    @NotNull
    private String certificado; */

    @Column(length = 100)
    @NotNull
    private String uuid;

    @Column
    @NotNull
    private Boolean isTimbrado;

    @Column
    @NotNull
    private Boolean status;
}
