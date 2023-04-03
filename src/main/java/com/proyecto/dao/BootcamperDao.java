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

public interface BootcamperDao extends JpaRepository<Bootcamper, Long> {
    @Query(value = "SELECT b FROM Bootcamper b LEFT JOIN b.telefonos t LEFT JOIN b.correos c")
    public List<Bootcamper> findAll(Sort sort);

    @Query(value = "SELECT b FROM Bootcamper b LEFT JOIN b.telefonos t LEFT JOIN b.correos c",
     countQuery = "SELECT COUNT(b) FROM Bootcamper b LEFT JOIN b.telefonos t LEFT JOIN b.correos c")
    public Page<Bootcamper> findAll(Pageable pageable);

    @Query(value = "SELECT b FROM Bootcamper b LEFT JOIN b.telefonos t LEFT JOIN b.correos c WHERE b.id = :id")
    public Bootcamper findById(long id);
}