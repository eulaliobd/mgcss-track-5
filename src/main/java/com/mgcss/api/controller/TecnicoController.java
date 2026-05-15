package com.mgcss.api.controller;

import com.mgcss.api.dto.TecnicoRequestDTO;
import com.mgcss.api.dto.TecnicoResponseDTO;
import com.mgcss.domain.Tecnico;
import com.mgcss.service.TecnicoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tecnicos")
@Tag(name = "3. Técnicos", description = "Operaciones relacionadas con los técnicos")
public class TecnicoController {

    private final TecnicoService tecnicoService;

    public TecnicoController(TecnicoService tecnicoService) {
        this.tecnicoService = tecnicoService;
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo técnico")
    public ResponseEntity<TecnicoResponseDTO> crear(@Valid @RequestBody TecnicoRequestDTO dto) {
        Tecnico nuevoTecnico = tecnicoService.crearTecnico(dto.getNombre(), dto.getEspecialidad());
        return ResponseEntity.status(HttpStatus.CREATED).body(mapearADTO(nuevoTecnico));
    }

    @PatchMapping("/{id}/desactivar")
    @Operation(summary = "Desactivar un técnico por su ID")
    public ResponseEntity<TecnicoResponseDTO> desactivar(@PathVariable Long id) {
        Tecnico tecnicoDesactivado = tecnicoService.desactivarTecnico(id);
        return ResponseEntity.ok(mapearADTO(tecnicoDesactivado));
    }

    @GetMapping("/disponibles")
    @Operation(summary = "Listar todos los técnicos que están activos")
    public ResponseEntity<List<TecnicoResponseDTO>> listarDisponibles() {
        List<TecnicoResponseDTO> lista = tecnicoService.listarTecnicosDisponibles().stream()
                .map(this::mapearADTO)
                .toList();
        return ResponseEntity.ok(lista);
    }

    private TecnicoResponseDTO mapearADTO(Tecnico t) {
        TecnicoResponseDTO dto = new TecnicoResponseDTO();
        dto.setId(t.getId());
        dto.setNombre(t.getNombre());
        dto.setEspecialidad(t.getEspecialidad());
        dto.setActivo(t.isActivo());
        dto.setExperiencia(t.getExperiencia());
        return dto;
    }
}