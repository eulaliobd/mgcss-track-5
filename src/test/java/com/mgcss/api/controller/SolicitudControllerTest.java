package com.mgcss.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgcss.api.dto.SolicitudRequestDTO;
import com.mgcss.domain.Solicitud;
import com.mgcss.service.SolicitudService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@WebMvcTest(SolicitudController.class)
class SolicitudControllerTest {

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
    
    @Test
    void deberiaListarSolicitudesYDevolver200() throws Exception {
        // Arrange: Simulamos que el servicio devuelve una lista vacía
		when(solicitudService.listarTodas()).thenReturn(List.of());

        // Act & Assert: Lanzamos un GET y esperamos un 200 OK
        mockMvc.perform(get("/api/solicitudes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaConsultarSolicitudPorIdYDevolver200() throws Exception {
        // Arrange: Creamos un objeto simulado (para que el mapeador no falle al leer nulos)
        Solicitud solicitudSimulada = new Solicitud();
        // Si tienes setters para estado o descripción, ponle unos valores por defecto aquí
        // solicitudSimulada.setDescripcion("Test");
        
        when(solicitudService.buscarPorId(1L)).thenReturn(solicitudSimulada);

        // Act & Assert
        mockMvc.perform(get("/api/solicitudes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaAsignarTecnicoYDevolver200() throws Exception {
        // Act & Assert: Simulamos la petición PUT a la URL con los dos IDs
        mockMvc.perform(put("/api/solicitudes/1/tecnico/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaCerrarSolicitudYDevolver200() throws Exception {
        // Act & Assert: Simulamos el PUT de cierre
        mockMvc.perform(put("/api/solicitudes/1/cerrar")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaReabrirSolicitudYDevolver200() throws Exception {
        // Act & Assert: Simulamos el PATCH de reapertura
        mockMvc.perform(patch("/api/solicitudes/1/reabrir")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}