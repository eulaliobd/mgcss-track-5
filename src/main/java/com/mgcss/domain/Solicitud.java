package com.mgcss.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
public class Solicitud {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Enumerated(EnumType.STRING)
	private Estado estado;
	
	@ManyToOne
	private Tecnico tecnico;
	@ManyToOne 
    private Cliente cliente;
	
	private String descripcion;
	private LocalDateTime fechaCreacion = LocalDateTime.now();
	
	@ElementCollection
	private List<CambioEstado> historialEstados = new ArrayList<>();
	
	private void registrarCambioEstado(Estado nuevoEstado) {
        this.historialEstados.add(new CambioEstado(nuevoEstado, LocalDateTime.now()));
    }

	public Solicitud() {
		this.estado = Estado.EN_PROCESO;
		registrarCambioEstado(getEstado());
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
		registrarCambioEstado(getEstado());
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
        registrarCambioEstado(getEstado());
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


	public String getDescripcion() {
		return descripcion;
	}


	public Cliente getCliente() {
		return cliente;
	}


	public void setDescripcion(String string) {
		this.descripcion = string;		
	}


	public void setCliente(Cliente cliente2) {
		this.cliente = cliente2;
	}
	
	public void reabrir() {
        if (this.estado != Estado.CERRADA) {
            throw new IllegalStateException("Solo se pueden reabrir solicitudes cerradas");
        }
        this.estado = Estado.EN_PROCESO;
        registrarCambioEstado(getEstado());
    }
	
	public List<CambioEstado> getHistorialEstados() {
        return historialEstados;
    }

}

