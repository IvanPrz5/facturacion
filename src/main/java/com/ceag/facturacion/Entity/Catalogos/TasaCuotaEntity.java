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
@Table(name="TasaCuota")
public class TasaCuotaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    @NotNull
    private String rangoFijo;
    
    @Column(length = 50)
    private String valorMinimo;
    
    @Column(length = 50)
    @NotNull
    private String valorMaximo;
    
    @Column(length = 50)
    @NotNull
    private String impuesto;
    
    @Column(length = 50)
    @NotNull
    private String factor;
    
    @Column(length = 50)
    @NotNull
    private String traslado;
    
    @Column(length = 50)
    @NotNull
    private String retencion;
    
    @Column
    @NotNull
    private Boolean status;
}

