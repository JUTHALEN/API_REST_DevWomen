package com.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.proyecto.dao.BootcampDao;
import com.proyecto.entities.Bootcamp;

@Service
public class BootcampServiceImpl implements BootcampService {

    @Autowired
    private BootcampDao bootcampDao;
    
    @Override
    public List<Bootcamp> findAll(Sort sort) {
        return bootcampDao.findAll(sort);
    }

    @Override
    public Page<Bootcamp> findAll(Pageable pageable) {
        return bootcampDao.findAll(pageable);
    }

    @Override
    public Bootcamp findById(long id) {
        return bootcampDao.findById(id);
    }

    @Override
    public void deleteById(long id) {
        bootcampDao.deleteById(id);
    }

    @Override
    public Bootcamp save(Bootcamp bootcamp) {
        return bootcampDao.save(bootcamp);
    }

    @Override
    public void delete(Bootcamp bootcamp) {
        bootcampDao.delete(bootcamp);
    }
    
}
