package com.proyecto.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.proyecto.entities.Bootcamp;

public interface BootcampService {
    public List<Bootcamp> findAll(Sort sort);

    public Page<Bootcamp> findAll(Pageable pageable);

    public Bootcamp findById(long id);

    public void deleteById(long id);

    public Bootcamp save(Bootcamp bootcamp);

    public void delete(Bootcamp bootcamp);

}
