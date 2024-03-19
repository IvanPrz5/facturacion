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
@Table(name="ImpuestosPrecargados")
public class ImpuestosPrecargadosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTrasladoPre;

    @Column(length = 50)
    @NotNull
    private String base;

    @Column(length = 50)
    @NotNull
    private String importeTraslado;

    @Column
    private Boolean isTrasladado;

    @Column(length = 200)
    @NotNull
    private String impuesto;

    @Column(length = 50)
    @NotNull
    private String tipoFactor;

    @Column(length = 50)
    @NotNull
    private String tasaCuota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idConceptoPrecargado")
    @JsonBackReference
    private ConceptosPrecargadosEntity idConceptoPrecargado;
}