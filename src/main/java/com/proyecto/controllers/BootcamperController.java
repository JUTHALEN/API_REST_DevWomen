package com.proyecto.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.http.fileupload.FileUpload;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.entities.Bootcamper;
import com.proyecto.services.BootcamperService;
import com.proyecto.utilities.FileUploadUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bootcampers")
public class BootcamperController {

    @Autowired
    private BootcamperService bootcamperService;

    @Autowired
    private FileUploadUtil fileUploadUtil;

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

    // Metodo que inserta un nuevo Bootcamper

    @PostMapping( consumes = "multipart/form-data")
    @Transactional
    public ResponseEntity<Map<String, Object>> insert(@Valid @RequestBody Bootcamper bootcamper,
            BindingResult result, @RequestParam(name = "file") MultipartFile file) throws IOException {

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

         /*Previamente a guardar un Bootcamp comprobamos si nos han enviado una imagen */
         if(!file.isEmpty()) {
            String fileCode = fileUploadUtil.saveFile(file.getOriginalFilename(), file); //recibe nombre del archivo y su contenido
            //Hemos lanzado una excepcion para arriba
            bootcamper.setFoto(fileCode + "-" + file.getOriginalFilename());
        }

        Bootcamper bootcamperDB = bootcamperService.save(bootcamper);
        /**
         * Crear la validación para saber si se ha guardado
         */
        try {
            if (bootcamperDB != null) {
                String mensaje = "Bootcamper se ha creado correctamente";
                responseAsMap.put("mensaje", mensaje);
                responseAsMap.put("Bootcamper", bootcamperDB);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);

            } else {
                String mensaje = "Bootcamper no se ha podido crear";
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
     *  */ 

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
        } else{
            responseEntity = new ResponseEntity<String>("No existe el bootcamper",HttpStatus.NO_CONTENT);
        }
    } catch (DataAccessException e) {
           e.getMostSpecificCause();
            String errorGrave = "Error grave";
            responseEntity = new ResponseEntity<String>(errorGrave, HttpStatus.INTERNAL_SERVER_ERROR);
            
        }
        return responseEntity;
    }

}
