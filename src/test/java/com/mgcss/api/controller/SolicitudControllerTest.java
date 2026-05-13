package com.mgcss.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgcss.api.dto.SolicitudRequestDTO;
import com.mgcss.service.SolicitudService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SolicitudController.class) // Levanta solo la capa web, no la base de datos [cite: 133, 315-317]
public class SolicitudControllerTest {

    @Autowired
    private MockMvc mockMvc; 

    @Autowired
    private ObjectMapper objectMapper; 

    @MockBean
    private SolicitudService solicitudService; 

    @Test
    void deberiaCrearSolicitudYDevolver201() throws Exception {
        // Arrange: Preparamos el pasaporte de entrada (DTO)
        SolicitudRequestDTO request = new SolicitudRequestDTO();
        request.setDescripcion("Pantalla rota");

        // Act & Assert: Lanzamos un POST y esperamos un 201 CREATED
        mockMvc.perform(post("/api/solicitudes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void deberiaDarError400SiLaDescripcionEstaVacia() throws Exception {
        // Arrange: Preparamos un DTO inválido (vacío) para probar nuestra @Valid
        SolicitudRequestDTO request = new SolicitudRequestDTO();
        request.setDescripcion(""); // Esto debería saltar la aduana

        // Act & Assert: Lanzamos un POST y esperamos un 400 BAD REQUEST 
        mockMvc.perform(post("/api/solicitudes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}