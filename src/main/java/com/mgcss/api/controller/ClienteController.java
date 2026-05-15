package com.mgcss.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mgcss.api.dto.ClienteRequestDTO;
import com.mgcss.api.dto.ClienteResponseDTO;
import com.mgcss.domain.Cliente;
import com.mgcss.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "2. Clientes", description = "Gestión de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping
    @Operation(summary = "Registrar cliente")
    public ResponseEntity<ClienteResponseDTO> crear(@Valid @RequestBody ClienteRequestDTO dto) {
        Cliente c = clienteService.crearCliente(dto.getNombre(), dto.getEmail(), dto.getTipoCliente());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapearADTO(c));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Modificar datos del cliente")
    public ResponseEntity<ClienteResponseDTO> modificar(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO dto) {
        Cliente c = clienteService.modificarDatosBasicos(
                id, 
                dto.getNombre(), 
                dto.getEmail(), 
                dto.getTipoCliente() 
        );
        return ResponseEntity.ok(mapearADTO(c));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consultar cliente")
    public ResponseEntity<ClienteResponseDTO> consultar(@PathVariable Long id) {
        return ResponseEntity.ok(mapearADTO(clienteService.consultarCliente(id)));
    }

    @GetMapping
    @Operation(summary = "Listar clientes")
    public List<ClienteResponseDTO> listar() {
        return clienteService.listarClientes().stream().map(this::mapearADTO).toList();
    }

    private ClienteResponseDTO mapearADTO(Cliente c) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setId(c.getId());
        dto.setNombre(c.getNombre());
        dto.setEmail(c.getEmail());
        dto.setTipoCliente(c.getTipoCliente());
        return dto;
    }
}