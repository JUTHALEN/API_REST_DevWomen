package com.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.dao.BootcamperDao;
import com.proyecto.entities.Bootcamper;

@Service
public class BootcamperServiceImpl implements BootcamperService {

    @Autowired
    private BootcamperDao bootcamperDao;

    @Override
    public List<Bootcamper> findAll(Sort sort) {
        return bootcamperDao.findAll(sort);
    }

    @Override
    public Page<Bootcamper> findAll(Pageable pageable) {
        return bootcamperDao.findAll(pageable);   
     }
     
    @Override
    public Bootcamper findById(long id) {
        return bootcamperDao.findById(id).get();
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        bootcamperDao.deleteById(id);
    }

    @Override
    @Transactional
    public Bootcamper save(Bootcamper bootcamper) {
        return bootcamperDao.save(bootcamper);
    }

    @Override
    public void delete(Bootcamper bootcamper) {
        bootcamperDao.delete(bootcamper);
    }

 

  
    
}
