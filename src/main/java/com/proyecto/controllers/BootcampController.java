package com.proyecto.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.proyecto.entities.Bootcamp;
import com.proyecto.model.FileUploadResponse;
import com.proyecto.services.BootcampService;
import com.proyecto.services.BootcamperService;
import com.proyecto.utilities.FileDownloadUtil;
import com.proyecto.utilities.FileUploadUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bootcamps")
public class BootcampController {

    @Autowired
    private BootcampService bootcampService;


    @Autowired
    private FileUploadUtil fileUploadUtil; 

    @Autowired
    private FileDownloadUtil fileDownloadUtil;

    // Metodo que encuentra los bootcamps
    @GetMapping
    public ResponseEntity<List<Bootcamp>> findAll(@RequestParam(name = "page", required = false) Integer page,
            @RequestParam(name = "size", required = false) Integer size) {

        ResponseEntity<List<Bootcamp>> responseEntity = null;

        List<Bootcamp> bootcamps = new ArrayList<>();

        Sort sortByNombre = Sort.by("nombre");

        if (page != null && size != null) {

            try {
                Pageable pageable = PageRequest.of(page, size, sortByNombre);
                Page<Bootcamp> bootcampsPaginados = bootcampService.findAll(pageable);
                bootcamps = bootcampsPaginados.getContent(); // Aqui estan los Bootcamps

                responseEntity = new ResponseEntity<List<Bootcamp>>(bootcamps, HttpStatus.OK);
            } catch (Exception e) {
                responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            // sin paginacion, pero con ordenamiento
            try {
                bootcamps = bootcampService.findAll(sortByNombre);
                responseEntity = new ResponseEntity<List<Bootcamp>>(bootcamps, HttpStatus.OK);
            } catch (Exception e) {
                responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        }

        return responseEntity;
    }
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable(name = "id") Integer id) {

        ResponseEntity<Map<String, Object>> responseEntity = null;
        Map<String, Object> responseAsMap = new HashMap<>();

        try {

            Bootcamp bootcamp = bootcampService.findById(id);

            if (bootcamp != null) {

                String successMessage = "Se ha encontrado el bootcamper con id: " + id;
                responseAsMap.put("mensaje", successMessage);
                responseAsMap.put("bootcamp", bootcamp);
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
    // Metodo que inserta un nuevo Bootcamp

    @PostMapping ( consumes = "multipart/form-data" )
    @Transactional
    public ResponseEntity<Map<String, Object>> insert
    (@Valid @RequestPart(name = "bootcamp") Bootcamp bootcamp,
    BindingResult result, @RequestPart(name = "file") MultipartFile file) throws IOException {

        Map<String, Object> responseAsMap = new HashMap<>();

        ResponseEntity<Map<String, Object>> responseEntity = null;

        /** Primero comprobar si hay errores en el Bootcamp recibido */
        if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {

                errorMessages.add(error.getDefaultMessage());

            }
            responseAsMap.put("errores", errorMessages);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity; // si hay error no quiero que se guarde el Bootcamp
        }
        
        /** Previamente a guardar un Bootcamp comprobamos si nos han enviado una imagen */
        if(!file.isEmpty()) {
            String fileCode = fileUploadUtil.saveFile(file.getOriginalFilename(), file); //recibe nombre del archivo y su contenido
            bootcamp.setLogo(fileCode + "-" + file.getOriginalFilename());

            /** Devolver informacion respecto al archivo recibido */
            FileUploadResponse fileUploadResponse = FileUploadResponse
            .builder()
            .fileName(fileCode + "-" + file.getOriginalFilename())
            .downloadURI("/bootcamps/downloadFile/" + fileCode + "-" + file.getOriginalFilename())
            .size(file.getSize())
            .build();

            responseAsMap.put("info de la imagen", fileUploadResponse);

        }

        Bootcamp bootcampDB = bootcampService.save(bootcamp);
        try {
            if (bootcampDB != null) { // Aqui estoy haciendo la validacion de si se ha guardado                
                String mensaje = "El Bootcamp se ha creado correctamente";
                responseAsMap.put("mensaje", mensaje);
                responseAsMap.put("Bootcamp", bootcampDB);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.CREATED);

            } else {
                String mensaje = "El Bootcamp no se ha creado";
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
    // Metodo que actualiza el bootcamp

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody Bootcamp bootcamp, BindingResult result,
            @PathVariable(name = "id") Integer id) {
        // Para que valide lo que llega

        Map<String, Object> responseAsMap = new HashMap<>();

        ResponseEntity<Map<String, Object>> responseEntity = null;
        /** Primero comprobar si hay errores en el bootcamp recibido */
        if (result.hasErrors()) {
            List<String> errorMessages = new ArrayList<>();
            for (ObjectError error : result.getAllErrors()) {

                errorMessages.add(error.getDefaultMessage());

            }
            responseAsMap.put("errores", errorMessages);

            responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.BAD_REQUEST);

            return responseEntity; // si hay error no quiero que se guarde el bootcamp
        }

        // Vinculamos el id que se recibe con el bootcamp
        bootcamp.setId(id);
        // Si no hay errores, entonces actualizamos el bootcamp.
        Bootcamp bootcampDB = bootcampService.save(bootcamp);
        try {
            if (bootcampDB != null) { // Aqui estoy haciendo la validacion de si se ha guardado
                String mensaje = "El bootcamp se ha actualizado correctamente";
                responseAsMap.put("mensaje", mensaje);
                responseAsMap.put("bootcamp", bootcampDB);
                responseEntity = new ResponseEntity<Map<String, Object>>(responseAsMap, HttpStatus.OK);

            } else {
                String mensaje = "El bootcamp no se ha actualizado";
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

    // Metodo que borra los bootcammmps
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBootcamp(@PathVariable(name = "id") Integer id){
        ResponseEntity<String> responseEntity = null;
       
        Bootcamp bootcamp = bootcampService.findById(id);
      
        try {
            if (bootcamp != null) {
            String mensaje = "El bootcamp se ha borrado correctamente";
            bootcampService.delete(bootcamp);
            responseEntity = new ResponseEntity<String>(mensaje, HttpStatus.OK);
        } else{
            responseEntity = new ResponseEntity<String>("No existe el bootcamp",HttpStatus.NO_CONTENT);
        }
    } catch (DataAccessException e) {
           e.getMostSpecificCause();
            String errorGrave = "Error grave";
            responseEntity = new ResponseEntity<String>(errorGrave, HttpStatus.INTERNAL_SERVER_ERROR);
            
        }
        return responseEntity;
    }

    /** Implementa filedownnload end point API */    

    @GetMapping("/downloadFile/{fileCode}") 
    public ResponseEntity<?> downloadFile(@PathVariable(name = "fileCode") String fileCode) {

        Resource resource = null;

        try {
            resource = fileDownloadUtil.getFileAsResource(fileCode);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }

        if (resource == null) {
            return new ResponseEntity<>("Archivo no encontrado", HttpStatus.NOT_FOUND);
        }

        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(contentType)) //MediaType de spring
        .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
        .body(resource);

    }

}
