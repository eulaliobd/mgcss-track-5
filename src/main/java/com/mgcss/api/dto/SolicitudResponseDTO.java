package com.mgcss.api.dto;

public class SolicitudResponseDTO {
    
    private Long id;
    private String estado;
    private String descripcion;

    public SolicitudResponseDTO() {
    	//Constructor vacío
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}