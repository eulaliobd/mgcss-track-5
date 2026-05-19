package com.mgcss.api.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgcss.api.dto.SolicitudRequestDTO;
import com.mgcss.domain.Cliente;
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
        SolicitudRequestDTO request = new SolicitudRequestDTO();
        request.setDescripcion("Pantalla rota");

        mockMvc.perform(post("/api/solicitudes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void deberiaDarError400SiLaDescripcionEstaVacia() throws Exception {
        SolicitudRequestDTO request = new SolicitudRequestDTO();
        request.setDescripcion(""); 

        mockMvc.perform(post("/api/solicitudes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void deberiaListarSolicitudesYDevolver200() throws Exception {
		when(solicitudService.listarTodas()).thenReturn(List.of());

        mockMvc.perform(get("/api/solicitudes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaConsultarSolicitudPorIdYDevolver200() throws Exception {
        Solicitud solicitudSimulada = new Solicitud();
        solicitudSimulada.setDescripcion("Test");
        
        when(solicitudService.buscarPorId(1L)).thenReturn(solicitudSimulada);

        mockMvc.perform(get("/api/solicitudes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaAsignarTecnicoYDevolver200() throws Exception {
        mockMvc.perform(put("/api/solicitudes/1/tecnico/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaCerrarSolicitudYDevolver200() throws Exception {
        mockMvc.perform(put("/api/solicitudes/1/cerrar")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaReabrirSolicitudYDevolver200() throws Exception {
        mockMvc.perform(patch("/api/solicitudes/1/reabrir")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @Test
    void deberiaAsignarClienteYDevolver200() throws Exception {
        // Arrange
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Ana");

        Solicitud solicitudSimulada = new Solicitud();
        solicitudSimulada.setId(1L);
        solicitudSimulada.setCliente(cliente);

        when(solicitudService.asignarCliente(1L, 1L)).thenReturn(solicitudSimulada);

        // Act & Assert
        mockMvc.perform(patch("/api/solicitudes/1/cliente/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}