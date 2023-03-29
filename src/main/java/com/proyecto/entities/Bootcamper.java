package com.proyecto.entities;


import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bootcampers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bootcamper extends Usuario {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)

    private int id;
    private double salario;    
    private Formacion formacion;
    private String foto;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaAlta;  


    public enum Formacion {
         GRADO_UNIVERSITARIO, GRADO_SUPERIOR, GRADO_MEDIO, BACHILLERATO, FORMACION_PROFESIONAL, OTRO;
    }

    //Constructor;
    public Bootcamper(String nombre, String primerApellido, String segundoApellido, Genero genero, String DNI,
            LocalDate fechaNacimiento, double salario, Formacion formacion, String foto, LocalDate fechaAlta) {
        super(); // invoca el constructor sin argumentos de la clase padre
        this.salario = salario;
        this.formacion = formacion;
        this.foto = foto;
        this.fechaAlta = fechaAlta;
        setNombre(nombre);
        setPrimerApellido(primerApellido);
        setSegundoApellido(segundoApellido);
        setGenero(genero);
        setDNI(DNI);
        setFechaNacimiento(fechaNacimiento);
    }


    /**
     * Creación de relaciones entre tablas
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idBootcamp")
    private Bootcamp bootcamp;

    //Relacionar con telefono y correo
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "bootcamper")
    private List<Telefono> telefonos;
   
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "bootcamper")
    private List<Correo> correos;



}

