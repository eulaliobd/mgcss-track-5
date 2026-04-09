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
}