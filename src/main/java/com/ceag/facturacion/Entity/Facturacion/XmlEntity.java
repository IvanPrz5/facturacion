package com.ceag.facturacion.Entity.Facturacion;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
@Table(name="Xml")
public class XmlEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Lob
    @Column(columnDefinition = "TEXT")
    private String xmlString;

    // @Lob
    @Column(columnDefinition = "TEXT")
    private String cvv;

    @Column(columnDefinition = "TEXT")
    private String cadenaOriginal;

    @Column
    private String uuid;

    @Column
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "idComprobante")
    private ComprobanteEntity idComprobante;
}
