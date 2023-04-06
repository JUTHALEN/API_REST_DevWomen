package com.proyecto.services;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.proyecto.entities.Bootcamper;

public interface BootcamperService {
    public List<Bootcamper> findAll(Sort sort); 
    public Bootcamper findById(long id); 
    public void deleteById(long id); 
    public Bootcamper save (Bootcamper bootcamper); 
    public Page<Bootcamper> findAll(Pageable pageable);
    public void delete(Bootcamper bootcamper);
   

}
