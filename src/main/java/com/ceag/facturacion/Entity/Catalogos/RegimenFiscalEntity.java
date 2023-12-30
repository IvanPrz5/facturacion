package com.ceag.facturacion.Entity.Catalogos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="RegimenFiscal")
public class RegimenFiscalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    @NotNull
    private String codigo;
    
    @Column(length = 200)
    @NotNull
    private String descripcion;
    
    @Column(length = 20)
    @NotNull
    private String fisica;
    
    @Column(length = 20)
    @NotNull
    private String moral;
    
    @Column
    @NotNull
    private Boolean status;
}