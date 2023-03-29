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
@Table(name = "idiomas")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Idioma implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    private Lenguage nombre;
    private String nivel;
    private boolean certificado;

    private enum Lenguage{
        INGLES, FRANCES, ALEMAN, ITALIANO, CHINO, JAPONES, ARABE, RUSO, PORTUGUES, ESPANOL;
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Bootcamper bootcamper;

    
    
}
