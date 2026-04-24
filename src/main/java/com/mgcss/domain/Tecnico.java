package com.mgcss.domain;

import jakarta.persistence.*;

@Entity
public class Tecnico {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private boolean activo;
	private int experiencia;
	
	private String nombre;
    private String especialidad;
	
	public Tecnico() {
		//Constructor vacío necesario para instanciar
	}
	
	public void activar() {
        this.activo = true;
    }

    public void desactivar() {
        this.activo = false;
    }

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		        
        this.activo = activo;
	}
	
	public void setExperiencia(int experiencia) {
        if (experiencia < 0) {
            throw new IllegalArgumentException("Los años de experiencia no pueden ser negativos");
        }
        
        this.experiencia = experiencia;
    }

	public int getExperiencia() {
		return experiencia;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEspecialidad() {
		return especialidad;
	}

	public void setEspecialidad(String especialidad) {
		this.especialidad = especialidad;
	}
	
	
	
}
