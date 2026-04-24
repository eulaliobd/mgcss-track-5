package com.mgcss.service;

import com.mgcss.domain.Cliente;
import com.mgcss.domain.ClienteRepository;
import com.mgcss.domain.TipoCliente;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock 
    private ClienteRepository clienteRepository;

    @InjectMocks 
    private ClienteService clienteService;

    @Test
    void deberiaModificarDatosBasicosDeClienteExistente() {
        
        Cliente clienteOriginal = new Cliente("Pepe", "pepe@antiguo.com", TipoCliente.STANDARD);
        
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(clienteOriginal));
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(i -> i.getArguments()[0]);

        Cliente modificado = clienteService.modificarDatosBasicos(1L, "Pepe Actualizado", "pepe@nuevo.com");

        assertEquals("Pepe Actualizado", modificado.getNombre());
        assertEquals("pepe@nuevo.com", modificado.getEmail());
        verify(clienteRepository).save(clienteOriginal); 
    }

    @Test
    void deberiaLanzarExcepcionAlModificarClienteInexistente() {
        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            clienteService.modificarDatosBasicos(99L, "Fantasma", "no@existe.com");
        });
    }
}