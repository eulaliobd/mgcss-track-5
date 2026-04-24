package com.mgcss.domain;

import jakarta.persistence.*;

// Nota: Siguiendo el Handout (Paso 2), se utiliza la misma clase para dominio y 
// persistencia para simplificar el desarrollo, manteniendo la separación en la capa de repositorios.
@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    private String email;
    
    @Enumerated(EnumType.STRING)
    private TipoCliente tipoCliente;

    public Cliente() {}

	public Cliente(String nombre, String email, TipoCliente tipo) {
		this.nombre = nombre;
		this.email = email;
		this.tipoCliente = tipo;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public TipoCliente getTipoCliente() {
		return tipoCliente;
	}

	public void setTipoCliente(TipoCliente tipoCliente) {
		this.tipoCliente = tipoCliente;
	}

    
}