package com.proyecto.services;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.proyecto.entities.Bootcamper;

public interface BootcamperService {
    public List<Bootcamper> findAll(); 
    public Bootcamper findById(long idBootcamper); 
    public void deleteById(long idBootcamper); 
    public Bootcamper save(Bootcamper bootcamper); 

}
