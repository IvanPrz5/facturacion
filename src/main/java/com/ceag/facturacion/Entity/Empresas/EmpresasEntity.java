package com.ceag.facturacion.Entity.Empresas;

import java.util.ArrayList;
import java.util.List;

import com.ceag.facturacion.Entity.Catalogos.RegimenFiscalEntity;
import com.ceag.facturacion.Entity.Clientes.ClientesEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name="Empresas")
public class EmpresasEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 50)
    @NotNull
    private String nombre;

    @Column(length = 50)
    @NotNull
    private String rfc;
    
    @Column(length = 50)
    @NotNull
    private String curp;

    @Column(length = 20)
    @NotNull
    private String codPostal;

    @Column(length = 50)
    @NotNull
    private String numCertificado;
    
    @Column(length = 200)
    @NotNull
    private String usuarioPac;
    
    @Column(length = 100)
    @NotNull
    private String contraseñaPac;

    @Column(length = 50)
    @NotNull
    private String fisica;

    @Column(length = 20)
    @NotNull
    private String passKey;

    @Column(length = 3000)
    @NotNull
    private String cerB64;
    
    @Column(length = 3000)
    @NotNull
    private String keyB64;

    @Column(columnDefinition = "TEXT")
    private String logo;

    @Column
    @NotNull
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "idRegimenFiscal")
    private RegimenFiscalEntity idRegimenFiscal;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "empresa_cliente", joinColumns = @JoinColumn(name = "empresa_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "cliente_id", referencedColumnName = "id"))
    private List<ClientesEntity> clientes = new ArrayList<>();
}
