package com.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.dao.TelefonoDao;
import com.proyecto.entities.Bootcamper;
import com.proyecto.entities.Telefono;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TelefonoServiceImpl implements TelefonoService {

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
    @Transactional
    public void deleteById(long idTelefono) {
       telefonoDao.deleteById(idTelefono);
    }

    @Override
    @Transactional
    public Telefono save(Telefono telefono) {
        return telefonoDao.save(telefono);
    }

    @Override
    @Transactional
    public void deleteByBootcamper(Bootcamper bootcamper) {
        telefonoDao.deleteByBootcamper(bootcamper);
    }

    @Override
    @Transactional
    public List<Telefono> findByBootcamper(Bootcamper bootcamper) {
        return telefonoDao.findByBootcamper(bootcamper);
    }
    
}
