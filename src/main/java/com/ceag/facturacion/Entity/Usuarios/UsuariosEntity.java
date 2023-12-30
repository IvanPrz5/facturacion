package com.ceag.facturacion.Entity.Usuarios;

import java.util.ArrayList;
import java.util.List;

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
@Table(name="Usuarios")
public class UsuariosEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 50)
    @NotNull
    private String nombre;
    
    @Column(length = 50)
    @NotNull
    private String apPaterno;
    
    @Column(length = 100)
    @NotNull
    private String email;
    
    @Column(length = 100)
    @NotNull
    private String password;
    
    @Column(length = 500)
    private String avatar = "https://cdn.vuetifyjs.com/images/cards/road.jpg";
    
    @Column
    @NotNull
    private Boolean status;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<RolesEntity> roles = new ArrayList<>();
}
