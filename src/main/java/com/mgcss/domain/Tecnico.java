package com.mgcss.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;



@Entity
public class Tecnico {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	private String especialidad;
	private boolean activo;
	private int experiencia;
	
	public Tecnico() {
		//Constructor vacío necesario para instanciar
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		        
        this.activo = activo;
	}
	
	public void setExperiencia(int experiencia) {
        // REGLA DE NEGOCIO: Proteger contra datos ilógicos
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
