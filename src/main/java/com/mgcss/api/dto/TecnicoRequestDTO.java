package com.mgcss.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public class TecnicoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre completo del técnico", example = "Carlos Pérez")
    private String nombre;

    @NotBlank(message = "La especialidad es obligatoria")
    @Schema(description = "Área de especialización", example = "Redes y Sistemas")
    private String especialidad;

    public String getNombre() { 
        return nombre; 
    }
    
    public void setNombre(String nombre) { 
        this.nombre = (nombre != null) ? nombre.trim() : null; 
    }

    public String getEspecialidad() { 
        return especialidad; 
    }
    
    public void setEspecialidad(String especialidad) { 
        this.especialidad = (especialidad != null) ? especialidad.trim() : null; 
    }
}