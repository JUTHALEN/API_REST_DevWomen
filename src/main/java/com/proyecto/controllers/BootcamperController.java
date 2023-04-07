package com.proyecto.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.entities.Bootcamp;
import com.proyecto.entities.Bootcamper;
import com.proyecto.services.BootcampService;
import com.proyecto.services.BootcamperService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bootcampers")
public class BootcamperController {

    @Autowired
    private BootcamperService bootcamperService;

    @Autowired
    private BootcampService bootcampService;

    /**
     * Metodo que encuentra los bootcampers
     */

    @GetMapping
    public ResponseEntity<List<Bootcamper>> findAll(@RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size) {

        ResponseEntity<List<Bootcamper>> responseEntity = null;

        List<Bootcamper> bootcampers = new ArrayList<>();

        Sort sortByNombre = Sort.by("nombre");

        if (page != null && size != null) {

            try {
                Pageable pageable = PageRequest.of(page, size, sortByNombre);
                Page<Bootcamper> bootcampersPaginados = bootcamperService.findAll(pageable);
                bootcampers = bootcampersPaginados.getContent();

                responseEntity = new ResponseEntity<List<Bootcamper>>(bootcampers, HttpStatus.OK);
            } catch (Exception e) {
                responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            // sin paginacion, pero con ordenamiento
            try {
                bootcampers = bootcamperService.findAll(sortByNombre);
                responseEntity = new ResponseEntity<>(bootcampers, HttpStatus.OK);
            } catch (Exception e) {
                responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }

        return responseEntity;
    }

    // Metodo que inserta un nuevo Bootcamp

    // @PostMapping
    // @Transactional
    // public ResponseEntity<Map<String, Object>> insert(@Valid @RequestPart(name = "bootcamper") 
    //                                                   Bootcamper bootcamper, @RequestParam(name = "bootcamp") 
    //                                                   Bootcamp bootcamp,
    //                                                   BindingResult result) {

    //     Map<String, Object> responseAsMap = new HashMap<>();
    //     ResponseEntity<Map<String, Object>> responseEntity = null;

    //     /** Primero comprobar si hay errores en el Bootcamper recibido */

    //     if (result.hasErrors()) {
    //         List<String> errorMessages = new ArrayList<>();
    //         for (ObjectError error : result.getAllErrors()) {
    //             errorMessages.add(error.getDefaultMessage());

    //         }
    //         responseAsMap.put("errores", errorMessages);

    //         responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
    //         return responseEntity; // si hay error no quiero que se guarde el Bootcamper
    //     }

    //     Bootcamp bootcampDB = bootcampService.save(bootcamp);
        
    //     /**
    //      * Crear la validación para saber si se ha guardado
    //      */
    //     try {
    //         if (bootcampDB != null) {
    //              Bootcamper bootcamperDB = bootcamperService.save(bootcamper);
    //             //  if (bootcamperDB != null) {
                    
    //                 bootcamper.setBootcamp(bootcamp);
    //                 bootcamperService.save(bootcamperDB);
                       
    //             // }
    //             String mensaje = "Bootcamper se ha creado correctamente";
    //             responseAsMap.put("mensaje", mensaje);
    //             responseAsMap.put("Bootcamper", bootcamperDB);
    //             responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);

    //         } else {
    //             String mensaje = "Bootcamper no se ha podido crear";
    //             responseAsMap.put("mensaje", mensaje);
    //             responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.NOT_ACCEPTABLE);
    //         }
    //     } catch (DataAccessException e) {

    //         String errorGrave = "Ha tenido lugar un error grave y la causa más probable puede ser" +
    //                 e.getMostSpecificCause();
    //         responseAsMap.put("errorGrave", errorGrave);
    //         responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
    //     }

    //     return responseEntity;
    // }
    @PostMapping("/bootcampers")
    @Transactional
    public ResponseEntity<Map<String, Object>> insert(@Valid @RequestPart(name = "bootcamper") Bootcamper bootcamper,
                                                      BindingResult result) {
        Map<String, Object> responseAsMap = new HashMap<>();
        ResponseEntity<Map<String, Object>> responseEntity = null;
    
        /** Primero comprobar si hay errores en el Bootcamper recibido */
        if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {
                errorMessages.add(error.getDefaultMessage());
            }
            responseAsMap.put("errores", errorMessages);
    
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);
            return responseEntity; // si hay error no quiero que se guarde el Bootcamper
        }
    
        Bootcamp bootcampDB = bootcampService.save(bootcamper.getBootcamp());
        /**
         * Crear la validación para saber si se ha guardado
         */
        try {
            if (bootcampDB != null) {
                bootcamper.setBootcamp(bootcampDB);
                Bootcamper bootcamperDB = bootcamperService.save(bootcamper);
                String mensaje = "Bootcamper se ha creado correctamente";
                responseAsMap.put("mensaje", mensaje);
                responseAsMap.put("Bootcamper", bootcamperDB);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);
            } else {
                String mensaje = "Bootcamp no se ha podido crear";
                responseAsMap.put("mensaje", mensaje);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (DataAccessException e) {
            String errorGrave = "Ha tenido lugar un error grave y la causa más probable puede ser " +
                    e.getMostSpecificCause();
            responseAsMap.put("errorGrave", errorGrave);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseEntity;
    }
    
    /**
     * 
     * Recupera un bootcamper por el id.
     * 
     * Va a responder a una peticion del tipo, por ejemplo:
     * 
     * http://localhost:8080/bootcamper/2
     * 
     */

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable(name = "id") Integer id) {

        ResponseEntity<Map<String, Object>> responseEntity = null;
        Map<String, Object> responseAsMap = new HashMap<>();

        try {

            Bootcamper bootcamper = bootcamperService.findById(id);

            if (bootcamper != null) {
            

                String successMessage = "Se ha encontrado el bootcamper con id: " + id;
                responseAsMap.put("mensaje", successMessage);
                responseAsMap.put("bootcamper", bootcamper);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);

            } else {

                String errorMessage = "No se ha encontrado el bootcamper con id:";
                responseAsMap.put("error", errorMessage);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.NOT_FOUND);

            }

        } catch (Exception e) {

            String errorGrave = "Error grave";

            responseAsMap.put("error", errorGrave);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return responseEntity;

    }

    /**
     * Metodo que actualiza los bootcampers
     */

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody Bootcamper bootcamper, BindingResult result,
            @PathVariable(name = "id") Integer id) {

        /**
         * Generar la validación de lo que se recibe
         */

        Map<String, Object> responseAsMap = new HashMap<>();

        ResponseEntity<Map<String, Object>> responseEntity = null;

        /** Primero comprobar si hay errores en el bootcamper recibido */

        if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {

                errorMessages.add(error.getDefaultMessage());

            }
            responseAsMap.put("errores", errorMessages);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity;
        }
        /**
         * Si hay errores no se guardará el bootcamper
         */

        /**
         * Vinculamos el id que se recibe con el bootcamper
         */

        bootcamper.setId(id);

        /**
         * Si no hay errores, entonces actualizamos el bootcamp.
         */

        Bootcamper bootcamperDB = bootcamperService.save(bootcamper);

        try {
            if (bootcamperDB != null) { // Aqui estoy haciendo la validacion de si se ha guardado
                String mensaje = "Bootcamper actualizado correctamente";
                responseAsMap.put("mensaje", mensaje);
                responseAsMap.put("bootcamp", bootcamperDB);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);

            } else {
                String mensaje = "Bootcamper no actualizado";
                responseAsMap.put("mensaje", mensaje);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (DataAccessException e) {

            String errorGrave = "Ha tenido lugar un error grave y la causa más probable puede ser" +
                    e.getMostSpecificCause();
            responseAsMap.put("errorGrave", errorGrave);
            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return responseEntity;
    }

    /**
     * Método que borra los bootcampers
     */

    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBootcamper(@PathVariable(name = "id") Integer id) {

        ResponseEntity<String> responseEntity = null;

        Bootcamper bootcamper = bootcamperService.findById(id);

        try {
            if (bootcamper != null) {
                String mensaje = "Bootcamper borrado correctamente";
                bootcamperService.delete(bootcamper);
                responseEntity = new ResponseEntity<String>(mensaje, HttpStatus.OK);
            } else {
                responseEntity = new ResponseEntity<String>("No existe el bootcamper", HttpStatus.NO_CONTENT);
            }
        } catch (DataAccessException e) {
            e.getMostSpecificCause();
            String errorGrave = "Error grave";
            responseEntity = new ResponseEntity<String>(errorGrave, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        return responseEntity;
    }

}
