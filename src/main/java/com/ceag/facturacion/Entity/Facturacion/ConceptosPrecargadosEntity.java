package com.ceag.facturacion.Entity.Facturacion;

import com.ceag.facturacion.Entity.Catalogos.ClaveProdServEntity;
import com.ceag.facturacion.Entity.Catalogos.ClaveUnidadEntity;
import com.ceag.facturacion.Entity.Catalogos.ObjetoImpEntity;

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
    private String cantidad;

    @Column(length = 500)
    @NotNull
    private String descripcion;

    @Column(length = 50)
    @NotNull
    private String valorUnitario;

    @Column(length = 50)
    @NotNull
    private String descuento;

    /*@Column(length = 50)
    @NotNull
    private Double importe;*/

    @Column
    @NotNull
    private Boolean isImpuesto;

    @Column
    @NotNull
    private Boolean status;

    /* @ManyToOne
    @JoinColumn(name = "idTrasladoPrecargado")
    private TrasladosPrecargadosEntity idTrasladoPrecargado; */
    @ManyToOne
    @JoinColumn(name = "idClaveProdServ")
    private ClaveProdServEntity idClaveProdServ;

    @ManyToOne
    @JoinColumn(name = "idObjetoImp")
    private ObjetoImpEntity idObjetoImp;

    @ManyToOne
    @JoinColumn(name = "idClaveUnidad")
    private ClaveUnidadEntity idClaveUnidad;
}