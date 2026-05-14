package com.mgcss.api.dto;

import com.mgcss.domain.TipoCliente;

public class ClienteResponseDTO {
    private Long id;
    private String nombre;
    private String email;
    private TipoCliente tipoCliente;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public TipoCliente getTipoCliente() { return tipoCliente; }
    public void setTipoCliente(TipoCliente tipoCliente) { this.tipoCliente = tipoCliente; }
}