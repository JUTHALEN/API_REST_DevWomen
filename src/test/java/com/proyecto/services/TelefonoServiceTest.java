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
import com.proyecto.dao.TelefonoDao;
import com.proyecto.entities.Bootcamper;
import com.proyecto.entities.Telefono;
import com.proyecto.entities.Bootcamper.Formacion;
import com.proyecto.entities.Bootcamper.Genero;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;


@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class TelefonoServiceTest {

    @Mock
    private TelefonoDao telefonoDao;

    @Mock
    private BootcamperDao bootcamperDao;

    @InjectMocks
    private TelefonoServiceImpl telefonoService;

    private Telefono telefono;

    @BeforeEach
    void setUp() {

        Bootcamper bootcamper = Bootcamper.builder()
                .nombre("Manuel")
                .id(20L)
                .primerApellido("Cañizares")
                .segundoApellido("Lopez")
                .salario(1300)
                .DNI("77623772U")
                .telefonos(null)
                .genero(Genero.HOMBRE)
                .fechaAlta(null)
                .fechaNacimiento(null)
                .formacion(Formacion.GRADO_SUPERIOR)
                .build();

        telefono = Telefono.builder()
                .id(3L)
                .numero("88376271")
                .bootcamper(bootcamper)
                .build();

    }

    @Test
    @DisplayName("Test de servicio para guardar un telefono")
    public void testSaveTelefono() {

        // given
        given(telefonoDao.save(telefono)).willReturn(telefono);

        // when
        Telefono telefonoGuardado = telefonoService.save(telefono);

        // then
        assertThat(telefonoGuardado).isNotNull();
    }


    @Test
    @DisplayName("Test que recupera una lista vacia de telefonos")
    public void testEmptyTelefono() {

        //given
        given(telefonoDao.findAll()).willReturn(Collections.emptyList());

        //when

        List<Telefono> telefonos = telefonoDao.findAll();

        //then
        assertThat(telefonos).isEmpty();

}
    
}
