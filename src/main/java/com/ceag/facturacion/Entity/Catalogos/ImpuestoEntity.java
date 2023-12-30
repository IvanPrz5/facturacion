package com.ceag.facturacion.Entity.Catalogos;

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
@Table(name="Impuesto")
public class ImpuestoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 50)
    @NotNull
    private String codigo;
    
    @Column(length = 50)
    @NotNull
    private String descripcion;

    @Column(length = 50)
    @NotNull
    private String retencion;

    @Column(length = 50)
    @NotNull
    private String traslado;

    @Column(length = 50)
    @NotNull
    private String localFederal;
    
    @Column
    @NotNull
    private Boolean status;
}   
