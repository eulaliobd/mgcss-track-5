package com.mgcss.api.controller;

import com.mgcss.api.dto.SolicitudRequestDTO;
import com.mgcss.api.dto.SolicitudResponseDTO;
import com.mgcss.domain.Solicitud; 
import com.mgcss.service.SolicitudService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/solicitudes") 
@Tag(name = "1. Solicitudes", description = "Operaciones relacionadas con la gestión de solicitudes")
public class SolicitudController {

    private final SolicitudService solicitudService;

    public SolicitudController(SolicitudService solicitudService) {
        this.solicitudService = solicitudService;
    }

    
    private SolicitudResponseDTO mapearADTO(Solicitud solicitud) {
        SolicitudResponseDTO dto = new SolicitudResponseDTO();
        dto.setId(solicitud.getId());
        dto.setEstado(solicitud.getEstado().name());         
        dto.setDescripcion(solicitud.getDescripcion());
        return dto;
    }

    @Operation(summary = "Crear una solicitud nueva")
    @PostMapping
    public ResponseEntity<SolicitudResponseDTO> crearSolicitud(@Valid @RequestBody SolicitudRequestDTO requestDTO) {
        
        Solicitud nuevaSolicitud = new Solicitud();
        
        nuevaSolicitud.setDescripcion(requestDTO.getDescripcion()); 
        
        
        solicitudService.crearSolicitud(nuevaSolicitud); 

        
        SolicitudResponseDTO responseDTO = mapearADTO(nuevaSolicitud);

        
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
    
    @Operation(summary = "Consultar una solicitud por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<SolicitudResponseDTO> consultarSolicitud(@PathVariable Long id) {
        
        Solicitud solicitud = solicitudService.buscarPorId(id);
        SolicitudResponseDTO responseDTO = mapearADTO(solicitud);
        return ResponseEntity.ok(responseDTO);
        
    }
    
    @Operation(summary = "Asignar un técnico a una solicitud")
    @PutMapping("/{solicitudId}/tecnico/{tecnicoId}")
    public ResponseEntity<Void> asignarTecnico(@PathVariable Long solicitudId, @PathVariable Long tecnicoId) {
        
        solicitudService.asignarTecnico(solicitudId, tecnicoId);
        
        return ResponseEntity.ok().build(); 
    }

    @Operation(summary = "Cerrar una solicitud")
    @PutMapping("/{id}/cerrar")
    public ResponseEntity<Void> cerrarSolicitud(@PathVariable Long id) {
        
        solicitudService.cerrarSolicitud(id);
        
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Reabrir una solicitud")
    @PatchMapping("/{id}/reabrir")
    public ResponseEntity<Void> reabrirSolicitud(@PathVariable Long id) {
        solicitudService.reabrirSolicitud(id);
        
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @Operation(summary = "Listar todas las solicitudes")
    public ResponseEntity<List<SolicitudResponseDTO>> listarSolicitudes() {
        return ResponseEntity.ok(solicitudService.listarTodas().stream().map(this::mapearADTO).toList());
    }
    
}