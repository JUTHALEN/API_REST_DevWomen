package com.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.proyecto.dao.TelefonoDao;
import com.proyecto.entities.Bootcamper;
import com.proyecto.entities.Telefono;

public class TelefonoServiceImpl implements TelefonoService{

    @Autowired
    private TelefonoDao telefonoDao;

    @Override
    public List<Telefono> findAll() {
        return telefonoDao.findAll();
    }

    @Override
    public Telefono findById(long idTelefono) {
        return telefonoDao.findById(idTelefono).get();
    }

    @Override
    public void deleteById(long idTelefono) {
       telefonoDao.deleteById(idTelefono);
    }

    @Override
    public Telefono save(Telefono telefono) {
        return telefonoDao.save(telefono);
    }

    @Override
    public void deleteByBootcamper(Bootcamper bootcamper) {
        telefonoDao.deleteByBootcamper(bootcamper);
    }

    @Override
    public List<Telefono> findByBootcamper(Bootcamper bootcamper) {
        return telefonoDao.findByBootcamper(bootcamper);
    }
    
}
