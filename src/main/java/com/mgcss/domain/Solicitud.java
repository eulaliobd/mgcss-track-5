package com.mgcss.domain;

import java.time.LocalDateTime;

public class Solicitud {

	private Long id;
	private Estado estado;
	private LocalDateTime fechaCreacion;
	private Tecnico tecnico;

	public Solicitud() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	
	public Tecnico getTecnico() {
		return tecnico;
	}

	public void cerrar() {		
		if (this.estado != Estado.EN_PROCESO) {
            throw new IllegalStateException("Solo se pueden cerrar solicitudes en proceso");
        }
        this.estado = Estado.CERRADA; 
      }

	public void asignarTecnico(Tecnico tecnico) {
        if (!tecnico.isActivo()) {
            throw new IllegalArgumentException("Solo se puede asignar un técnico activo a la solicitud");
        }
        
        this.tecnico=tecnico;
	}

}

