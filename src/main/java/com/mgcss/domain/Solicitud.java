package com.mgcss.domain;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class Solicitud {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Cliente cliente;
	
	private String descripcion;
	
	private LocalDateTime fechaCreacion = LocalDateTime.now();
	
	@Enumerated(EnumType.STRING)
	private Estado estado = Estado.ABIERTA;
	
	@ManyToOne
	private Tecnico tecnico;
	
	private LocalDateTime fechaCierre;

	public Solicitud() {
		//Constructor vacío necesario para instanciar
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
		
		
        if (this.tecnico == null) {
            throw new IllegalStateException("No se puede cerrar una solicitud sin técnico asignado");
        }
        
        this.estado = Estado.CERRADA; 
        this.fechaCierre = LocalDateTime.now();
      }

	public void asignarTecnico(Tecnico tecnico) {
		// Proteger el estado cerrado
        if (this.estado == Estado.CERRADA) {
            throw new IllegalStateException("No se puede asignar un técnico a una solicitud ya cerrada");
        }
        
        // El técnico debe estar activo
		if (!tecnico.isActivo()) {
            throw new IllegalArgumentException("Solo se puede asignar un técnico activo a la solicitud");
        }
        
        this.tecnico=tecnico;
	}

}

