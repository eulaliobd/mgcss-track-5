package com.mgcss.api.dto;

import com.mgcss.domain.TipoCliente;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public abstract class ClienteBaseDTO {
    
    @NotBlank(message = "El nombre es obligatorio")
    @Schema(example = "Ana García")
    private String nombre;

    @NotBlank(message = "El email es obligatorio")
    @Email(message = "Formato de email inválido")
    @Schema(example = "ana@email.com")
    private String email;

    @Schema(description = "Tipo de cliente", example = "PARTICULAR")
    private TipoCliente tipoCliente;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
    	this.nombre = (nombre != null) ? nombre.trim() : null;
    }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { 
    	this.email = (email != null) ? email.trim().toLowerCase() : null; 
    }
    
    public TipoCliente getTipoCliente() { return tipoCliente; }
    public void setTipoCliente(TipoCliente tipoCliente) { this.tipoCliente = tipoCliente; }
}