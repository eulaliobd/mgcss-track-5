package com.mgcss.integration;

import com.mgcss.domain.Cliente;
import com.mgcss.domain.ClienteRepository;
import com.mgcss.domain.TipoCliente;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Tag("integration")
class ClienteRepositoryIntegrationTest {

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void deberiaGuardarYRecuperarCliente() {
        // Arrange
        Cliente cliente = new Cliente("Marta", "marta@mail.com", TipoCliente.PREMIUM);

        // Act
        Cliente guardado = clienteRepository.save(cliente);

        // Assert
        assertNotNull(guardado.getId(), "H2 debe generar un ID");
        
        Cliente recuperado = clienteRepository.findById(guardado.getId()).orElseThrow();
        assertEquals("Marta", recuperado.getNombre());
        assertEquals("marta@mail.com", recuperado.getEmail());
    }
}