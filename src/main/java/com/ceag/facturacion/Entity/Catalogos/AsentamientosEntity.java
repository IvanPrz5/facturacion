package com.ceag.facturacion.Entity.Catalogos;

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
@Table(name="Asentamientos")
public class AsentamientosEntity {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(length = 50)
    @NotNull
    private String codigo;
    
    @Column(length = 150)
    @NotNull
    private String nombre;
    
    @Column(length = 150)
    @NotNull
    private String tipo;
    
    @Column 
    @NotNull
    private Boolean status;
    
    @ManyToOne
    @JoinColumn(name="idCodigoPostal")
    private CodigoPostalEntity idCodigoPostal;

    /* @Override
    public String toString() {
        String colonias = id + " , " + nombre;
        return colonias;
    } */
}
