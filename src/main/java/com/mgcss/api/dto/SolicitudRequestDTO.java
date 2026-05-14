package com.mgcss.api.dto;

import jakarta.validation.constraints.NotBlank;

public class SolicitudRequestDTO {
    
    @NotBlank(message = "La descripción no puede estar vacía")
    private String descripcion;

    public SolicitudRequestDTO() {
    	//Constructor vacío
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
