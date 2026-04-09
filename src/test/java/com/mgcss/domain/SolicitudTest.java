package com.mgcss.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
}