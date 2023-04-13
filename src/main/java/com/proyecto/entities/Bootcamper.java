package com.proyecto.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bootcampers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Bootcamper implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(min = 3, max = 25, message = "El nombre tiene que estar entre 3 y 25 caracteres")
    private String nombre;
    private String primerApellido;
    private String segundoApellido;

    @Enumerated(EnumType.STRING)
    private Genero genero;
    private String DNI;
    private double salario;

    @Enumerated(EnumType.STRING)
    private Formacion formacion;

    private String foto;
    private LocalDate fechaNacimiento;
    private LocalDate fechaAlta;

    public enum Genero {
        HOMBRE, MUJER, OTRO
    }

    public enum Formacion {
        GRADO_UNIVERSITARIO, GRADO_SUPERIOR, GRADO_MEDIO, BACHILLERATO, FORMACION_PROFESIONAL, OTRO
    }

    /**
     * Creación de relaciones entre tablas
     */
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "idBootcamp")
    // @JsonBackReference
    private Bootcamp bootcamp;

    // Relacionar con telefono y correo
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "bootcamper")
    private List<Telefono> telefonos;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "bootcamper")
    private List<Correo> correos;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "bootcamper")
    private List<Idioma> idiomas;

}
