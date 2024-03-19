package com.ceag.facturacion.Entity.Facturacion;

import java.time.LocalDateTime;
import java.util.List;

import com.ceag.facturacion.Entity.Empresas.EmpresasEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

    @Column(length = 50)
    private LocalDateTime fechaCreacion;
    /*@Column(length = 50)
    @NotNull
    private Double importe;*/

    @Column(length = 50)
    @NotNull
    private String idClaveProdServ;
    
    @Column(length = 1000)
    private String claveProdServDesc;

    @Column(length = 50)
    @NotNull
    private String idClaveUnidad;

    @Column(length = 100)
    @NotNull
    private String unidad;

    @Column(length = 50)
    @NotNull
    private String idObjetoImp;

    @Column
    @NotNull
    private Boolean isImpuesto;

    @Column
    @NotNull
    private Boolean status;


    /* @ManyToOne
    @JoinColumn(name = "idClaveProdServ")
    private ClaveProdServEntity idClaveProdServ; */

    /* @ManyToOne
    @JoinColumn(name = "idObjetoImp")
    private ObjetoImpEntity idObjetoImp; */

    /* @ManyToOne
    @JoinColumn(name = "idClaveUnidad")
    private ClaveUnidadEntity idClaveUnidad; */

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idEmpresa")
    private EmpresasEntity idEmpresa;
    
    @JsonManagedReference
    @OneToMany(mappedBy = "idConceptoPrecargado")
    private List<ImpuestosPrecargadosEntity> datosImpuestos;
    
}