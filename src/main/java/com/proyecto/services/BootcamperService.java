package com.proyecto.services;


import java.util.List;

import com.proyecto.entities.Bootcamper;

public interface BootcamperService {
    public List<Bootcamper> findAll(); 
    public Bootcamper findById(long idBootcamper); 
    public void deleteById(long idBootcamper); 
    public Bootcamper save(Bootcamper bootcamper); 

}
