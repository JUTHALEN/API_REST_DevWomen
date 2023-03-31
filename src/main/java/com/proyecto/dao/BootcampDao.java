package com.proyecto.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.proyecto.entities.Bootcamp;

@Repository
public interface BootcampDao extends JpaRepository<Bootcamp, Long> {
    
    @Query(value = "select b from Bootcamp b left join fetch b.bootcampers") 
     
     public List<Bootcamp> findAll(Sort sort);
     @Query(value = "select b from Bootcamp b left join fetch b.bootcampers", 
     countQuery = "select count(b) from Bootcamp b left join b.bootcampers")
  
     public Page<Bootcamp> findAll(Pageable pageable);
  @Query(value = "select b from Bootcamp b left join fetch b.bootcampers where b.id = :id") 
  public Bootcamp findById(long id);
}
