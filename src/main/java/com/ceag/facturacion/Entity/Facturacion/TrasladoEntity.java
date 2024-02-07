package com.ceag.facturacion.Entity.Facturacion;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name="Traslado")
public class TrasladoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    @NotNull
    private String base;

    @Column(length = 200)
    @NotNull
    private String impuesto;

    @Column(length = 50)
    @NotNull
    private String tipoFactor;

    @Column(length = 50)
    @NotNull
    private String tasaCuota;

    @Column(length = 50)
    @NotNull
    private String importe;

    @Column
    private Boolean isTrasladado;

    @Column
    @NotNull
    private Boolean status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idConcepto")
    @JsonBackReference
    private ConceptoEntity idConcepto;
}
