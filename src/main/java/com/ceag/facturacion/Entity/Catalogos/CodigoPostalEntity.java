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
@Table(name="CodigoPostal")
public class CodigoPostalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @NotNull
    private Boolean status;

    @ManyToOne
    @JoinColumn(name="idEstado")
    private EstadoEntity idEstado;

    @ManyToOne
    @JoinColumn(name="idMunicipio")
    private MunicipioEntity idMunicipio;

    @ManyToOne
    @JoinColumn(name="idLocalidad")
    private LocalidadEntity idLocalidad;

}
