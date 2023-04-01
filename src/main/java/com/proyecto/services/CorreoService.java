package com.proyecto.services;

import java.util.List;

import com.proyecto.entities.Bootcamper;
import com.proyecto.entities.Correo;

public interface CorreoService {
    public List<Correo> findAll(); 
    public Correo findById(long idCorreo); 
    public void deleteById(long idCorreo); 
    public Correo save (Correo correo); 
    public void deleteByBootcamper (Bootcamper bootcamper);
    public List<Correo> findByBootcamper(Bootcamper bootcamper);
}
