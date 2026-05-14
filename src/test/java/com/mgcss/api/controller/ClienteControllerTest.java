package com.mgcss.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mgcss.api.dto.ClienteRequestDTO;
import com.mgcss.domain.Cliente;
import com.mgcss.domain.TipoCliente;
import com.mgcss.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClienteService clienteService;

    @Test
    void deberiaCrearClienteYDevolver201() throws Exception {
        // Arrange
        ClienteRequestDTO request = new ClienteRequestDTO();
        request.setNombre("Ana");
        request.setEmail("ana@mail.com");
        request.setTipoCliente(TipoCliente.STANDARD);

        Cliente clienteSimulado = new Cliente("Ana", "ana@mail.com", TipoCliente.STANDARD);
        clienteSimulado.setId(1L);

        when(clienteService.crearCliente(any(), any(), any())).thenReturn(clienteSimulado);

        // Act & Assert
        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void deberiaDarError400AlCrearClienteInvalido() throws Exception {
        // Arrange
        ClienteRequestDTO request = new ClienteRequestDTO();
        request.setNombre(""); 
        request.setEmail("correo-malo");

        // Act & Assert
        mockMvc.perform(post("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deberiaModificarClienteYDevolver200() throws Exception {
        // Arrange
        ClienteRequestDTO request = new ClienteRequestDTO();
        request.setNombre("Ana Modificada");
        request.setEmail("ana.nueva@mail.com");
        request.setTipoCliente(TipoCliente.PREMIUM);

        Cliente clienteSimulado = new Cliente("Ana Modificada", "ana.nueva@mail.com", TipoCliente.PREMIUM);
        clienteSimulado.setId(1L);

        when(clienteService.modificarDatosBasicos(eq(1L), any(), any(), any())).thenReturn(clienteSimulado);

        // Act & Assert
        mockMvc.perform(put("/api/clientes/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaConsultarClientePorIdYDevolver200() throws Exception {
        // Arrange
        Cliente clienteSimulado = new Cliente("Ana", "ana@mail.com", TipoCliente.STANDARD);
        clienteSimulado.setId(1L);

        when(clienteService.consultarCliente(1L)).thenReturn(clienteSimulado);

        // Act & Assert
        mockMvc.perform(get("/api/clientes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void deberiaListarClientesYDevolver200() throws Exception {
        // Arrange
        when(clienteService.listarClientes()).thenReturn(List.of(new Cliente(), new Cliente()));

        // Act & Assert
        mockMvc.perform(get("/api/clientes")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}