package com.ceag.facturacion.Entity.Facturacion;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="TimbreFiscal")
public class TimbreFiscalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    @NotNull
    private String uuid;

    @Column(length = 50)
    @NotNull
    private LocalDateTime fechaTimbrado;

    @Column(length = 50)
    @NotNull
    private LocalDateTime rfcProvCer;

    @Column(length = 500)
    @NotNull
    private String selloCfdi;

    @Column(length = 50)
    @NotNull
    private String noCertificadoSat;

    @Column(length = 500)
    @NotNull
    private String selloSat;

    @Column
    @NotNull
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "idComprobante")
    private ComprobanteEntity idComprobanteEntity;
}
