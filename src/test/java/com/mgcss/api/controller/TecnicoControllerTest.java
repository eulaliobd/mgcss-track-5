package com.mgcss.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgcss.api.dto.TecnicoRequestDTO;
import com.mgcss.domain.Tecnico;
import com.mgcss.service.TecnicoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TecnicoController.class)
class TecnicoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TecnicoService tecnicoService;

    @Test
    void deberiaCrearTecnicoYDevolver201() throws Exception {
        TecnicoRequestDTO request = new TecnicoRequestDTO();
        request.setNombre("Carlos Pérez");
        request.setEspecialidad("Redes y Sistemas");

        Tecnico tecnicoSimulado = new Tecnico();
        tecnicoSimulado.setId(1L);
        tecnicoSimulado.setNombre("Carlos Pérez");
        tecnicoSimulado.setEspecialidad("Redes y Sistemas");
        tecnicoSimulado.setActivo(true);

        when(tecnicoService.crearTecnico(any(), any())).thenReturn(tecnicoSimulado);

        mockMvc.perform(post("/api/tecnicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void deberiaDarError400AlCrearTecnicoInvalido() throws Exception {
        TecnicoRequestDTO request = new TecnicoRequestDTO();
        request.setNombre(""); 
        request.setEspecialidad(""); 

        mockMvc.perform(post("/api/tecnicos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deberiaDesactivarTecnicoYDevolver200() throws Exception {
        Tecnico tecnicoSimulado = new Tecnico();
        tecnicoSimulado.setId(1L);
        tecnicoSimulado.setActivo(false);

        when(tecnicoService.desactivarTecnico(1L)).thenReturn(tecnicoSimulado);

        mockMvc.perform(patch("/api/tecnicos/1/desactivar")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaListarTecnicosDisponiblesYDevolver200() throws Exception {
        when(tecnicoService.listarTecnicosDisponibles()).thenReturn(List.of(new Tecnico(), new Tecnico()));

        mockMvc.perform(get("/api/tecnicos/disponibles")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}