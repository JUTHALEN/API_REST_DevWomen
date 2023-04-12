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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.proyecto.entities.Bootcamp;
import com.proyecto.entities.Bootcamper;
import com.proyecto.entities.Bootcamp.Orientacion;
import com.proyecto.services.BootcampService;
import com.proyecto.services.BootcamperService;
import com.proyecto.utilities.FileDownloadUtil;
import com.proyecto.utilities.FileUploadUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
// Para seguir el enfoque BDD con Mockito
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.Month;


@SpringBootTest // levanta el contexto de Spring completamente para lo que necesitamos
                // no como la anterior
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BootcampControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BootcampService bootcampService;

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
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    void testGuardarBootcamp() throws Exception {

        //given

        Bootcamp bootcamp = Bootcamp.builder()
                .nombre("bootcamp")
                .orientacion(Orientacion.BACK_END)
                .descripcion(null)
                .fechaInicio(LocalDate.of(2023, Month.JANUARY, 23))
                .fechaFin(LocalDate.of(2023, Month.APRIL, 14))
                .language(Bootcamp.Language.INGLES)
                .build();

        // when

        String jsonStringBootcamp = objectMapper.writeValueAsString(bootcamp);
        System.out.println(jsonStringBootcamp);
        ResultActions response = mockMvc
                    .perform(post("/bootcampers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(jsonStringBootcamp));

        // then

        response.andDo(print())
                    .andExpect(status().isUnauthorized());

    }

    }
