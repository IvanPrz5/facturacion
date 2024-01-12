package com.ceag.facturacion.Entity.Facturacion;

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
@Table(name="ConceptosPrecargados")
public class ConceptosPrecargadosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    @NotNull
    private String claveProdServ;

    @Column(length = 200)
    @NotNull
    private String prodServ;

    @Column(length = 50)
    @NotNull
    private String cantidad;

    @Column(length = 50)
    @NotNull
    private String claveUnidad;

    @Column(length = 100)
    @NotNull
    private String unidad;

    @Column(length = 500)
    @NotNull
    private String descripcion;

    @Column(length = 50)
    @NotNull
    private Double valorUnitario;

    @Column(length = 50)
    @NotNull
    private Double descuento;

    @Column(length = 50)
    @NotNull
    private Double importe;

    @Column(length = 50)
    @NotNull
    private String objetoImp;

    @Column
    @NotNull
    private Boolean isImpuesto;

    @Column
    @NotNull
    private Boolean status;

    /* @ManyToOne
    @JoinColumn(name = "idTrasladoPrecargado")
    private TrasladosPrecargadosEntity idTrasladoPrecargado; */
}