package com.mgcss.integration; 

import com.mgcss.domain.Cliente;
import com.mgcss.domain.ClienteRepository;
import com.mgcss.domain.Solicitud;
import com.mgcss.domain.SolicitudRepository;
import com.mgcss.domain.TipoCliente;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.junit.jupiter.api.Tag;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // Levanta el contexto parcial de Spring con H2 [cite: 133-139]
@ActiveProfiles("test") // Le dice a Spring que lea el application-test.yml que acabamos de crear
@Tag("integration") // Etiqueta obligatoria del Paso 6 del Handout [cite: 152]
class SolicitudRepositoryIntegrationTest {

    // Spring inyectará automáticamente nuestros JpaSolicitudRepository gracias a la magia de la infraestructura
    @Autowired
    private SolicitudRepository solicitudRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Test
    void deberiaGuardarYRecuperarSolicitudConExito() { // [cite: 134-137]
        // 1. Arrange: Creamos datos reales (SIN MOCKS) [cite: 141]
        Cliente cliente = new Cliente();
        cliente.setNombre("Empresa ACME");
        cliente.setEmail("contacto@acme.com");
        cliente.setTipoCliente(TipoCliente.STANDARD); 
        
        // Guardamos el cliente primero para que la BD le asigne un ID
        cliente = clienteRepository.save(cliente); 

        Solicitud solicitud = new Solicitud();
        solicitud.setCliente(cliente);
        solicitud.setDescripcion("El servidor principal no arranca");

        // 2. Act: Guardamos la solicitud en la base de datos H2
        Solicitud solicitudGuardada = solicitudRepository.save(solicitud);

        // 3. Assert: Verificamos la integridad [cite: 137]
        assertNotNull(solicitudGuardada.getId(), "La base de datos debería haber generado un ID");
        
        // Recuperamos desde la base de datos
        Solicitud recuperada = solicitudRepository.findById(solicitudGuardada.getId()).orElseThrow();
        assertEquals("El servidor principal no arranca", recuperada.getDescripcion());
        assertEquals("Empresa ACME", recuperada.getCliente().getNombre());
    }
}