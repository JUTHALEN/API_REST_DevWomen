package com.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.dao.IdiomaDao;
import com.proyecto.entities.Bootcamper;
import com.proyecto.entities.Idioma;

@Service
public class IdiomaServiceImpl implements IdiomaService{
    
    @Autowired
    private IdiomaDao idiomaDao;

    @Override
    public List<Idioma> findAll() {
        return idiomaDao.findAll();
    }

    @Override
    public Idioma findById(long idIdioma) {
        return idiomaDao.findById(idIdioma).get();
    }

    @Override
    public void deleteById(long idIdioma) {
        idiomaDao.deleteById(idIdioma);
    }

    @Override
    public Idioma save(Idioma Idioma) {
        return idiomaDao.save(Idioma);
    }

    @Override
    public void deleteByBootcamper(Bootcamper bootcamper) {
        idiomaDao.deleteByBootcamper(bootcamper);
    }

    @Override
    public List<Idioma> findByBootcamper(Bootcamper bootcamper) {
        return idiomaDao.findByBootcamper(bootcamper);
    }
}
