package com.mgcss.service;

import com.mgcss.domain.Tecnico;
import com.mgcss.domain.TecnicoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TecnicoServiceTest {

    @Mock 
    private TecnicoRepository tecnicoRepository;

    @InjectMocks 
    private TecnicoService tecnicoService;

    @Test
    void deberiaDesactivarUnTecnicoExistente() {
        // Arrange
        Tecnico tecnico = new Tecnico();
        tecnico.setNombre("Ana");
        tecnico.activar();
        
        when(tecnicoRepository.findById(1L)).thenReturn(Optional.of(tecnico));
        when(tecnicoRepository.save(any(Tecnico.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        Tecnico resultado = tecnicoService.desactivarTecnico(1L);

        // Assert
        assertFalse(resultado.isActivo());
        verify(tecnicoRepository).save(tecnico); 
    }
    
    @Test
    void deberiaCrearTecnicoActivo() {
        // Arrange
        when(tecnicoRepository.save(any(Tecnico.class))).thenAnswer(i -> i.getArguments()[0]);
        
        // Act
        Tecnico nuevo = tecnicoService.crearTecnico("Pedro", "Hardware");
        
        // Assert
        assertTrue(nuevo.isActivo());
        assertEquals("Pedro", nuevo.getNombre());
        assertEquals("Hardware", nuevo.getEspecialidad());
        verify(tecnicoRepository).save(any(Tecnico.class));
    }

    @Test
    void deberiaLanzarExcepcionAlDesactivarTecnicoInexistente() {
        // Arrange
        when(tecnicoRepository.findById(99L)).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            tecnicoService.desactivarTecnico(99L);
        });
    }

    @Test
    void deberiaListarSoloTecnicosDisponibles() {
        // Arrange
        Tecnico t1 = new Tecnico(); 
        t1.activar();
        
        Tecnico t2 = new Tecnico(); 
        t2.desactivar(); 
        
        when(tecnicoRepository.findAll()).thenReturn(List.of(t1, t2));
        
        // Act
        List<Tecnico> disponibles = tecnicoService.listarTecnicosDisponibles();
        
        // Assert
        assertEquals(1, disponibles.size(), "Solo debería devolver el técnico activo");
        assertTrue(disponibles.get(0).isActivo());
    }
    
}
