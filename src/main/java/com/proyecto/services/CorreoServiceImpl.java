package com.proyecto.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyecto.dao.CorreoDao;
import com.proyecto.entities.Bootcamper;
import com.proyecto.entities.Correo;

@Service
public class CorreoServiceImpl implements CorreoService {

    @Autowired
    private CorreoDao correoDao;

    @Override
    public List<Correo> findAll() {
        return correoDao.findAll();
    }

    @Override
    public Correo findById(long idCorreo) {
        return correoDao.findById(idCorreo).get();
    }

    @Override
    @Transactional
    public void deleteById(long idCorreo) {
        correoDao.deleteById(idCorreo);
    }

    @Override
    @Transactional
    public Correo save(Correo correo) {
        return correoDao.save(correo);
    }

    @Override
    @Transactional
    public void deleteByBootcamper(Bootcamper bootcamper) {
        correoDao.deleteByBootcamper(bootcamper);
    }

    @Override
    @Transactional
    public List<Correo> findByBootcamper(Bootcamper bootcamper) {
        return correoDao.findByBootcamper(bootcamper);
    }

   
    
}
