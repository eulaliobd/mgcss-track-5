package com.mgcss.domain;

public class Tecnico {

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
	
}
