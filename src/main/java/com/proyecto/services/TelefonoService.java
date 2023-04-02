package com.proyecto.services;

import java.util.List;

import com.proyecto.entities.Bootcamper;
import com.proyecto.entities.Telefono;

public interface TelefonoService {
    public List<Telefono> findAll(); 
    public Telefono findById(long idTelefono);
    public void deleteById(long idTelefono); 
    public Telefono save (Telefono telefono); 
    public void deleteByBootcamper (Bootcamper bootcamper);
    public List<Telefono> findByBootcamper(Bootcamper bootcamper);
}

