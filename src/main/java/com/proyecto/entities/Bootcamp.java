package com.proyecto.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "bootcamps")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Bootcamp implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    
    @NotEmpty(message = "El nombre no puede estar vacío")
    @Size(min = 4, max = 25, message = "El nombre tiene que estar entre 4 y 25 caracteres")
    private String nombre;

    private String logo;
    @Enumerated(EnumType.STRING)
    private Orientacion orientacion;
    private String descripcion;

    @JsonFormat(pattern = "yyyy-MM-dd")      
    private LocalDate fechaInicio;  

    @JsonFormat(pattern = "yyyy-MM-dd")      
    private LocalDate fechaFin;  

    @Enumerated(EnumType.STRING)
    private Language language;

    public enum Orientacion {
        BACK_END,FRONT_END,FULL_STACK
    }


    /**
     * Creación de relaciones entre tablas
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, mappedBy = "bootcamp" ) 
    @JsonIgnore
    private List<Bootcamper> bootcampers;
    
    public enum Language{
        INGLES, FRANCES, ALEMAN, ITALIANO, CHINO, JAPONES, ARABE, RUSO, PORTUGUES, ESPANOL, OTRO
    }


}

