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

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class) //para utilizar Mockito
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BootcamperServiceTest {


    @Mock
    private BootcamperDao bootcamperDao;

    @Mock
    private BootcampDao bootcampDao;

    @InjectMocks
    private BootcamperServiceImpl bootcamperService;

    private Bootcamper bootcamper;

    @BeforeEach
    void setUp() {

        Bootcamp bootcamp = Bootcamp.builder()
        .nombre("Bootcamp formacion")
        .descripcion(null)
        .orientacion(Orientacion.BACK_END)
        .language(Language.FRANCES)
        .fechaInicio(null)
        .fechaFin(null)
        .build();

        bootcamper = Bootcamper.builder()
        .nombre("Alejandra")
        .id(20L)
        .primerApellido("Gutierrez")
        .segundoApellido("Garcia")
        .salario(1400)
        .DNI("123424255F")
        .genero(Genero.MUJER)
        .fechaAlta(null)
        .fechaNacimiento(null)
        .formacion(Formacion.BACHILLERATO)
        .bootcamp(bootcamp)
        .build();


    }

    @Test
    @DisplayName("Test de servicio para guardar un bootcamper")
    public void testSaveBootcamper() {

        //given
        given(bootcamperDao.save(bootcamper)).willReturn(bootcamper);

        //when
        Bootcamper bootcamperGuardado = bootcamperService.save(bootcamper);

        //then
        assertThat(bootcamperGuardado).isNotNull();
    }

    @Test
    @DisplayName("Test que recupera una lista vacia de bootcampers")
    public void testEmptyBootcamper() {

        //given
        given(bootcamperDao.findAll()).willReturn(Collections.emptyList());

        //when

        List<Bootcamper> bootcampers = bootcamperDao.findAll();

        //then
        assertThat(bootcampers).isEmpty();

    
}
    
}
