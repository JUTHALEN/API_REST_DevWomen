package com.proyecto.services;

import java.util.List;

import com.proyecto.entities.Bootcamp;

public interface BootcampService {
    public List<Bootcamp> findAll(); 
    public Bootcamp findById(int idBootcamp); 
    public void deleteById(int idBootcamp);
    public void save (Bootcamp bootcamp); 
    public void delete(Bootcamp bootcamp);
}
