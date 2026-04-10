package com.mgcss.domain;

public class Tecnico {

	private boolean activo;
	private String nombre;
	private int experiencia;
	
	public Tecnico() {
		//Constructor vacío necesario para instanciar
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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
	
}
