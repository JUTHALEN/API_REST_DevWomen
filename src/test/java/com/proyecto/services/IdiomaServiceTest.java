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
import com.proyecto.dao.IdiomaDao;
import com.proyecto.entities.Bootcamper;
import com.proyecto.entities.Idioma;
import com.proyecto.entities.Bootcamper.Formacion;
import com.proyecto.entities.Bootcamper.Genero;
import com.proyecto.entities.Idioma.Language;
import com.proyecto.entities.Idioma.Nivel;

import static org.assertj.core.api.Assertions.assertThat;

import static org.mockito.BDDMockito.given;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class IdiomaServiceTest {

    @Mock
    private IdiomaDao idiomaDao;

    @Mock
    private BootcamperDao bootcamperDao;

    @InjectMocks
    private IdiomaServiceImpl idiomaService;

    private Idioma idioma;

    @BeforeEach
    void setUp() {

        Bootcamper bootcamper = Bootcamper.builder()
                .nombre("Manuel")
                .id(20L)
                .primerApellido("Cañizares")
                .segundoApellido("Lopez")
                .salario(1300)
                .DNI("77623772U")
                .idiomas(null)
                .genero(Genero.HOMBRE)
                .fechaAlta(null)
                .fechaNacimiento(null)
                .formacion(Formacion.GRADO_SUPERIOR)
                .build();

        idioma = Idioma.builder()
                .id(3L)
                .certificado(false)
                .language(Language.ALEMAN)
                .nivel(Nivel.B1)
                .bootcamper(bootcamper)
                .build();

    }

    @Test
    @DisplayName("Test de servicio para guardar un Idioma")
    public void testSaveIdioma() {

        // given
        given(idiomaDao.save(idioma)).willReturn(idioma);

        // when
        Idioma idiomaGuardado = idiomaService.save(idioma);

        // then
        assertThat(idiomaGuardado).isNotNull();
    }


    @Test
    @DisplayName("Test que recupera una lista vacia de idiomas")
    public void testEmptyIdioma() {

        //given
        given(idiomaDao.findAll()).willReturn(Collections.emptyList());

        //when

        List<Idioma> idiomas = idiomaDao.findAll();

        //then
        assertThat(idiomas).isEmpty();

}
    
    
}
