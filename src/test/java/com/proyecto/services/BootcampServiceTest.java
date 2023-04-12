package com.proyecto.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

import com.proyecto.dao.BootcampDao;
import com.proyecto.dao.BootcamperDao;
import com.proyecto.entities.Bootcamp;
import com.proyecto.entities.Bootcamper;
import com.proyecto.entities.Bootcamp.Language;
import com.proyecto.entities.Bootcamp.Orientacion;
import com.proyecto.entities.Bootcamper.Formacion;
import com.proyecto.entities.Bootcamper.Genero;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class) 
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BootcampServiceTest {


    @Mock
    private BootcampDao bootcampDao;
    
    @Mock
    private BootcamperDao bootcamperDao;

    @InjectMocks
    private BootcampServiceImpl bootcampService;

    private Bootcamp bootcamp;

    @BeforeEach
    void setUp() {
        bootcamp = Bootcamp.builder()
                .nombre("Bootcamp formacion")
                .descripcion(null)
                .orientacion(Orientacion.FRONT_END)
                .language(Language.ITALIANO)
                .fechaInicio(null)
                .fechaFin(null)
                .bootcampers((new ArrayList<>()))
                .build();
    
        Bootcamper bootcamper = Bootcamper.builder()
                .nombre("Pablo")
                .id(20L)
                .primerApellido("Gomez")
                .segundoApellido("Lopez")
                .salario(1700)
                .DNI("837475672L")
                .genero(Genero.HOMBRE)
                .fechaAlta(null)
                .fechaNacimiento(null)
                .formacion(Formacion.GRADO_SUPERIOR)
                .bootcamp(bootcamp)
                .build();
    
        bootcamp.getBootcampers().add(bootcamper); 
    }
    


    @Test
    @DisplayName("Test de servicio para guardar un bootcamp")
    public void testSaveBootcamp() {

        //given
        given(bootcampDao.save(bootcamp)).willReturn(bootcamp);

        //when
        Bootcamp bootcampGuardado = bootcampService.save(bootcamp);

        //then
        assertThat(bootcampGuardado).isNotNull();
    }


    @Test
    @DisplayName("Test que recupera una lista vacia de bootcamps")
    public void testEmptyBootcamp() {

        //given
        given(bootcampDao.findAll()).willReturn(Collections.emptyList());

        //when

        List<Bootcamp> bootcamps = bootcampDao.findAll();

        //then
        assertThat(bootcamps).isEmpty();

    
}

    
}
