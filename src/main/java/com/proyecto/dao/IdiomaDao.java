package com.proyecto.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proyecto.entities.Bootcamper;
import com.proyecto.entities.Idioma;

@Repository
public interface IdiomaDao extends JpaRepository <Idioma, Integer> {

    long deleteByBootcamper (Bootcamper bootcamper);    
    List <Idioma> findByBootcamper (Bootcamper bootcamper);
    
}