package com.mgcss.integration;

import com.mgcss.domain.Tecnico;
import com.mgcss.domain.TecnicoRepository;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Tag("integration")
class TecnicoRepositoryIntegrationTest {

    @Autowired
    private TecnicoRepository tecnicoRepository;

    @Test
    void deberiaGuardarYRecuperarTecnico() {
        // Arrange
        Tecnico tecnico = new Tecnico();
        tecnico.setNombre("Carlos");
        tecnico.setEspecialidad("Hardware");
        tecnico.activar();

        // Act
        Tecnico guardado = tecnicoRepository.save(tecnico);

        // Assert
        assertNotNull(guardado.getId(), "H2 debe generar un ID");
        
        Tecnico recuperado = tecnicoRepository.findById(guardado.getId()).orElseThrow();
        assertEquals("Carlos", recuperado.getNombre());
        assertEquals("Hardware", recuperado.getEspecialidad());
        assertTrue(recuperado.isActivo());
    }
}