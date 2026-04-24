package com.mgcss.service;

import com.mgcss.domain.Tecnico;
import com.mgcss.domain.TecnicoRepository;
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
class TecnicoServiceTest {

    @Mock // Creamos un "doble" del repositorio [cite: 51]
    private TecnicoRepository tecnicoRepository;

    @InjectMocks // Inyectamos el mock en el servicio
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
        verify(tecnicoRepository).save(tecnico); // Verificamos que se llamó al save [cite: 63]
    }
}
