package com.proyecto.entities;


import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
    private String numero;


    /**
     * Creación de relaciones entre tablas
     */
    
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Bootcamper bootcamper;



}
