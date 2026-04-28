package com.mgcss.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SolicitudTest {

    @Test
    void no_se_puede_cerrar_una_solicitud_abierta() {
        // Preparar el escenario
        Solicitud solicitud = new Solicitud();
        solicitud.setEstado(Estado.ABIERTA); 

        // Intentar cerrar y verificar que lanza excepción
        assertThrows(IllegalStateException.class, solicitud::cerrar);
    }
    
    @Test
    void solo_se_puede_asignar_un_tecnico_activo() {
        // Preparar el escenario
        Solicitud solicitud = new Solicitud();
        
        Tecnico tecnicoActivo = new Tecnico();
        tecnicoActivo.setActivo(true); 
        
        Tecnico tecnicoInactivo = new Tecnico();
        tecnicoInactivo.setActivo(false);

        // Asignación válida funciona (no lanza excepción)
        solicitud.asignarTecnico(tecnicoActivo);
        // Aquí podríamos hacer un assert para comprobar que solicitud.getTecnico() == tecnicoActivo

        // Asignación inválida falla (lanza excepción)
        assertThrows(IllegalArgumentException.class, () -> {
            solicitud.asignarTecnico(tecnicoInactivo);
        });
    }
    
    @Test
    void los_getters_devuelven_datos() {
        // Arranque
        Solicitud solicitud = new Solicitud();
        solicitud.setEstado(Estado.EN_PROCESO); 
        
        Tecnico tecnico = new Tecnico();
        tecnico.setActivo(true);
        
        
        solicitud.asignarTecnico(tecnico);
        solicitud.cerrar();
        
        //Llamamos a los getters para sumar coverage
        assertEquals(Estado.CERRADA, solicitud.getEstado());
        assertEquals(tecnico, solicitud.getTecnico());
        assertTrue(tecnico.isActivo());
    }
    
    @Test
    void no_se_puede_asignar_tecnico_a_solicitud_cerrada() {
        // Preparamos una solicitud cerrada y un técnico válido
        Solicitud solicitud = new Solicitud();
        solicitud.setEstado(Estado.CERRADA); // Asegúrate de usar el nombre exacto de tu estado
        
        Tecnico tecnico = new Tecnico();
        tecnico.setActivo(true);

        // Intentamos asignarlo y esperamos que explote
        assertThrows(IllegalStateException.class, () -> {
            solicitud.asignarTecnico(tecnico);
        });
    }
    
    @Test
    void no_se_puede_cerrar_solicitud_sin_tecnico_asignado() {
        Solicitud solicitud = new Solicitud();
        solicitud.setEstado(Estado.EN_PROCESO); 
        
        assertThrows(IllegalStateException.class, solicitud::cerrar);
    }

    @Test
    void se_puede_cerrar_solicitud_correcta_con_tecnico() {
        Solicitud solicitud = new Solicitud();
        solicitud.setEstado(Estado.EN_PROCESO);
        
        Tecnico tecnico = new Tecnico();
        tecnico.setActivo(true);
        
        solicitud.asignarTecnico(tecnico);
        solicitud.cerrar(); 
        
        assertEquals(Estado.CERRADA, solicitud.getEstado()); 
    }
    
    @Test
    void deberiaReabrirSolicitudCerrada() {
        // Arrange: Preparamos la solicitud cumpliendo todas tus reglas de negocio
        Solicitud solicitud = new Solicitud();
        
        // 1. Creamos y asignamos un técnico (usa tu método de asignar)
        Tecnico tecnico = new Tecnico();
        tecnico.activar();
        solicitud.asignarTecnico(tecnico); 
        
        // 2. La pasamos a en proceso y la cerramos
        solicitud.setEstado(Estado.EN_PROCESO); 
        solicitud.cerrar();
        
        // Act: Intentamos reabrirla
        solicitud.reabrir();
        
        // Assert: Verificar el estado final
        assertEquals(Estado.EN_PROCESO, solicitud.getEstado(), "La solicitud reabierta debe estar EN_PROCESO");
    }
    
    
}