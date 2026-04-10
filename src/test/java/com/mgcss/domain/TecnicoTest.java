package com.mgcss.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TecnicoTest {

    @Test
    void no_se_pueden_asignar_anios_de_experiencia_negativos() {
        Tecnico tecnico = new Tecnico();
        
        assertThrows(IllegalArgumentException.class, () -> {
            tecnico.setExperiencia(-1); 
        });
    }

    @Test
    void se_pueden_asignar_anios_de_experiencia_validos() {
        Tecnico tecnico = new Tecnico();
        tecnico.setExperiencia(5); 

        assertEquals(5, tecnico.getExperiencia());
    }
}