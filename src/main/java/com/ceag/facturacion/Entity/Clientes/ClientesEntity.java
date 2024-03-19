package com.ceag.facturacion.Entity.Clientes;

import com.ceag.facturacion.Entity.Catalogos.RegimenFiscalEntity;
import com.ceag.facturacion.Entity.Catalogos.UsoCFDIEntity;

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
@Table(name="Clientes")
public class ClientesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50)
    @NotNull
    private String rfc;

    @Column(length = 200)
    @NotNull
    private String nombre;

    @Column(length = 50)
    @NotNull
    private String domicilioFiscal;

    @Column
    @NotNull
    private Boolean status = true;

    @ManyToOne
    @JoinColumn(name = "regimenFiscal")
    private RegimenFiscalEntity regimenFiscal;

    /* @ManyToOne
    @JoinColumn(name = "usoCfdi")
    private UsoCFDIEntity usoCfdi; */
}
