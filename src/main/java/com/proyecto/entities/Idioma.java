package com.proyecto.entities;

import java.io.Serializable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Lenguage nombre;
    private Nivel nivel;
    private boolean certificado;

    public enum Lenguage{
        INGLES, FRANCES, ALEMAN, ITALIANO, CHINO, JAPONES, ARABE, RUSO, PORTUGUES, ESPANOL
    }

    public enum Nivel {
        A1, A2, B1, B2, C1,C2
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Bootcamper bootcamper;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Bootcamp bootcamp;
    
}
