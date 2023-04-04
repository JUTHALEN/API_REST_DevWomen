package com.proyecto.services;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.proyecto.entities.Bootcamper;

public interface BootcamperService {
    public List<Bootcamper> findAll(Sort sort); //Busca todo
    public Bootcamper findById(long idBootcamper); //Busca por Id
    public void deleteById(long idBootcamper); //Borra
    public Bootcamper save (Bootcamper bootcamper); //Guarda y actualiza
    public Page<Bootcamper> findAll(Pageable pageable);
    public void delete(Bootcamper bootcamper);
   

}
