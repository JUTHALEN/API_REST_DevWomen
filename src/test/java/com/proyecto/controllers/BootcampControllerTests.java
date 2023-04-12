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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proyecto.entities.Bootcamp;
import com.proyecto.entities.Bootcamper;
import com.proyecto.entities.Bootcamp.Language;
import com.proyecto.entities.Bootcamp.Orientacion;
import com.proyecto.entities.Bootcamper.Formacion;
import com.proyecto.entities.Bootcamper.Genero;
import com.proyecto.services.BootcampService;
import com.proyecto.services.BootcamperService;
import com.proyecto.utilities.FileDownloadUtil;
import com.proyecto.utilities.FileUploadUtil;

import static org.mockito.ArgumentMatchers.any;

// Para seguir el enfoque BDD con Mockito
// Para importar directamente el metodo, el import static
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BootcampControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BootcampService bootcampService;

    @MockBean
    private BootcamperService bootcamperService;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FileUploadUtil fileUploadUtil;

    @MockBean
    private FileDownloadUtil fileDownloadUtil;

    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setUp() {

        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void testGuardarBootcamp() throws Exception {
        // given - Datos dados

        Bootcamp bootcamp = Bootcamp.builder()
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
                .bootcamp(bootcamp)
                .fechaNacimiento(null)
                .formacion(Formacion.GRADO_SUPERIOR)
                .build();

        given(bootcampService.save(any(Bootcamp.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when - Accion
        String jsonStringProduct = objectMapper.writeValueAsString(bootcamp);
        System.out.println(jsonStringProduct);
        ResultActions response = mockMvc
                .perform(post("/bootcamps")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonStringProduct));

        // then - Resulta esperado
        response.andDo(print())
                .andExpect(status().isUnauthorized()); // se espera un Unauthorized por el endpoint
        // .andExpect()
        // .andExpect(jsonPath("$.nombre", is(producto.getNombre())))
        // .andExpect(jsonPath("$.descripcion", is(producto.getDescripcion())));

    }

    @Test
    @WithMockUser(username = "elisabetagullo@gmail.com", authorities = { "ROLE_ADMIN", "ROLE_USER" }) // usuario
                                                                                                      // mockeado-serializado
    void testGuardarBootcampConUserMockeado() throws Exception {

        // given - Datos dados

        Bootcamp bootcamp = Bootcamp.builder()
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
                .bootcamp(bootcamp)
                .fechaNacimiento(null)
                .formacion(Formacion.GRADO_SUPERIOR)
                .build();
                
        given(bootcampService.save(any(Bootcamp.class)))
                .willAnswer(invocation -> invocation.getArgument(0));

        // when - Accion
        String jsonStringProduct = objectMapper.writeValueAsString(bootcamp);
        System.out.println(jsonStringProduct);
        ResultActions response = mockMvc
                .perform(post("/bootcamps")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonStringProduct));

        // then - Resulta esperado
        response.andDo(print())
                .andExpect(status().isCreated()) // se espera un created por el endpoint
                .andExpect(jsonPath("$.nombre", is(bootcamp.getNombre())))
                .andExpect(jsonPath("$.descripcion", is(bootcamp.getDescripcion())));

    }

}
