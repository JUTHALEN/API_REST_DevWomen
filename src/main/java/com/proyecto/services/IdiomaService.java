package com.proyecto.services;

import java.util.List;

import com.proyecto.entities.Bootcamper;
import com.proyecto.entities.Idioma;

public interface IdiomaService {
    public List<Idioma> findAll();
    public Idioma findById(int idIdioma); 
    public void deleteById(int idIdioma); 
    public void save (Idioma Idioma); 
    public void deleteByBootcamper (Bootcamper bootcamper);
    public List<Idioma> findByBootcamper(Bootcamper bootcamper);
}
