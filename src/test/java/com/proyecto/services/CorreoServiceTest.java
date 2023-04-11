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

import com.proyecto.dao.BootcamperDao;
import com.proyecto.dao.CorreoDao;
import com.proyecto.entities.Bootcamper;
import com.proyecto.entities.Correo;
import com.proyecto.entities.Bootcamper.Formacion;
import com.proyecto.entities.Bootcamper.Genero;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class CorreoServiceTest {

    @Mock
    private CorreoDao correoDao;

    @Mock
    private BootcamperDao bootcamperDao;

    @InjectMocks
    private CorreoServiceImpl correoService;

    private Correo correo;

    @BeforeEach
    void setUp() {

        Bootcamper bootcamper = Bootcamper.builder()
                .nombre("Pablo")
                .id(20L)
                .primerApellido("Gomez")
                .segundoApellido("Lopez")
                .salario(1700)
                .DNI("837475672L")
                .correos(null)
                .genero(Genero.HOMBRE)
                .fechaAlta(null)
                .fechaNacimiento(null)
                .formacion(Formacion.GRADO_SUPERIOR)
                .build();

        correo = Correo.builder()
                .id(2L)
                .email("prueba@gmail.com")
                .bootcamper(bootcamper)
                .build();

    }

    @Test
    @DisplayName("Test de servicio para guardar un correo")
    public void testSaveCorreo() {

        // given
        given(correoDao.save(correo)).willReturn(correo);

        // when
        Correo correoGuardado = correoService.save(correo);

        // then
        assertThat(correoGuardado).isNotNull();
    }


    @Test
    @DisplayName("Test que recupera una lista vacia de correos")
    public void testEmptyCorreo() {

        //given
        given(correoDao.findAll()).willReturn(Collections.emptyList());

        //when

        List<Correo> correos = correoDao.findAll();

        //then
        assertThat(correos).isEmpty();

}

}