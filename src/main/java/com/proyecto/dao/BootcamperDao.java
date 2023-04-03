package com.proyecto.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.proyecto.entities.Bootcamper;

@Repository
public interface BootcamperDao extends JpaRepository <Bootcamper,Long> {
    @Query(value = "select b from Bootcamper b left join fetch b.telefonos left join b.correos left join b.idiomas")
    public List<Bootcamper> findAll(Sort sort);
    
    @Query(value = "select b from Bootcamper b left join fetch b.telefonos left join b.correos left join b.idiomas",
    countQuery = "select count(b) from Bootcamper b")
    public Page<Bootcamper> findAll(Pageable pageable);

    @Query(value = "select b from Bootcamper b left join fetch b.telefonos left join b.correos left join b.idiomas where b.id = :id") //Consulta parametro con nombre
    public Bootcamper findById(long id);
}