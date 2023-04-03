package com.proyecto.entities;


import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "telefonos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Telefono implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "El numero no puede estar vacío")
    @Size(min = 4, max = 25, message = "El numero tiene que estar entre 4 y 25 caracteres")
    private String numero;


    /**
     * Creación de relaciones entre tablas
     */
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Bootcamper bootcamper;



}
