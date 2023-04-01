package com.proyecto.services;


import java.util.List;

import com.proyecto.entities.Bootcamper;

public interface BootcamperService {
    public List<Bootcamper> findAll(); //Busca todo
    public Bootcamper findById(long idBootcamper); //Busca por Id
    public void deleteById(long idBootcamper); //Borra
    public Bootcamper save (Bootcamper bootcamper); //Guarda y actualiza

}
