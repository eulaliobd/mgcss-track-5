package com.mgcss.domain; 

import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;

@Embeddable
public class CambioEstado {
    private Estado estado;
    private LocalDateTime fechaCambio;

    public CambioEstado() {}

    public CambioEstado(Estado estado, LocalDateTime fechaCambio) {
        this.estado = estado;
        this.fechaCambio = fechaCambio;
    }

    public Estado getEstado() { return estado; }
    public LocalDateTime getFechaCambio() { return fechaCambio; }
}