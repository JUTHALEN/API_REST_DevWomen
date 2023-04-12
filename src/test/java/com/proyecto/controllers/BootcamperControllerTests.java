package com.proyecto.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.proyecto.entities.Bootcamp;
import com.proyecto.entities.Bootcamper;
import com.proyecto.entities.Correo;
import com.proyecto.entities.Idioma;
import com.proyecto.entities.Telefono;
import com.proyecto.entities.Bootcamp.Language;
import com.proyecto.entities.Bootcamp.Orientacion;
import com.proyecto.entities.Bootcamper.Formacion;
import com.proyecto.entities.Bootcamper.Genero;
import com.proyecto.entities.Idioma.Nivel;
import com.proyecto.services.BootcamperService;
import com.proyecto.services.TelefonoService;
import com.proyecto.utilities.FileDownloadUtil;
import com.proyecto.utilities.FileUploadUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.mockito.ArgumentMatchers.any;
// Para seguir el enfoque BDD con Mockito
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = Replace.NONE)//para que use nuestra base de datos de sql, en vez de una base de datos en memoria
// replace none para que la use tal cual, no la sustituya
@WithMockUser(username = "llanos@llanos",
authorities = {"ADMIN", "USER"}) 
public class BootcamperControllerTests {   

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BootcamperService bootcamperService;

    @Autowired
    private ObjectMapper objectMapper; //coge objeto de Java que hemos creado y lo convierte a un JSON (serializar)

    @MockBean
    private FileUploadUtil fileUploadUtil;

    @MockBean
    private FileDownloadUtil fileDownloadUtil;

    @Autowired
    private WebApplicationContext context; //Mockito simula las dependencias, por eso hay que explicarle que esto es una aplicación web
    // así el mockMvc actúa con seguridad listo para hacer las pruevas a los endpoints

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void testGuardarBootcamper() throws Exception {

        //given

        Bootcamp bootcamp = Bootcamp.builder()
                .nombre("bootcamp")
                .orientacion(Orientacion.BACK_END)
                .descripcion(null)
                .fechaInicio(LocalDate.of(2023, Month.JANUARY, 23))
                .fechaFin(LocalDate.of(2023, Month.APRIL, 14))
                .language(Bootcamp.Language.INGLES)
                .build();

                List<Telefono> telefonos = new ArrayList<>();
                telefonos.add(Telefono.builder()
                .numero("666000000")
                .build());

                List<Correo> correos = new ArrayList<>();
                correos.add(Correo.builder()
                .email("bootcamper@bootcamp.es")
                .build());

                List<Idioma> idiomas = new ArrayList<>();
                idiomas.add(Idioma.builder()
                .language(Idioma.Language.INGLES)
                .nivel(Nivel.B2)
                .certificado(true)
                .build());


        Bootcamper bootcamper = Bootcamper.builder()
        .nombre("bootcamper")
        .primerApellido("apellido1")
        .segundoApellido("apellido2")
        .genero(Genero.MUJER)
        .DNI("00000000C")
        .salario(1200.00)
        .formacion(Formacion.GRADO_UNIVERSITARIO)
        .fechaNacimiento(LocalDate.of(1990, Month.APRIL, 12))
        .fechaAlta(LocalDate.of(2023, Month.APRIL, 23))
        .bootcamp(bootcamp)
        .telefonos(telefonos)
        .correos(correos)
        .idiomas(idiomas)
        .build();

        given(bootcamperService.save(any(Bootcamper.class)))
        .willAnswer(invocation -> invocation.getArgument(0));


    // when

    String jsonStringBootcamper = objectMapper.writeValueAsString(bootcamper);
        System.out.println(jsonStringBootcamper);
    ResultActions response = mockMvc
                .perform(post("/bootcampers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonStringBootcamper));

    // then

    response.andDo(print())
                .andExpect(status().isUnauthorized());

    }

}