package com.mgcss.service;

import com.mgcss.domain.Tecnico;
import com.mgcss.domain.TecnicoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TecnicoService {

    private final TecnicoRepository tecnicoRepository;

    public TecnicoService(TecnicoRepository tecnicoRepository) {
        this.tecnicoRepository = tecnicoRepository;
    }

    public Tecnico crearTecnico(String nombre, String especialidad) {
        Tecnico tecnico = new Tecnico();
        tecnico.setNombre(nombre);
        tecnico.setEspecialidad(especialidad);
        tecnico.activar(); 
        return tecnicoRepository.save(tecnico); 
    }

    public Tecnico desactivarTecnico(Long id) {
        Tecnico tecnico = tecnicoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Técnico no encontrado"));
        
        tecnico.desactivar(); 
        return tecnicoRepository.save(tecnico); 
    }

    public List<Tecnico> listarTecnicosDisponibles() {
        return tecnicoRepository.findAll().stream()
                .filter(Tecnico::isActivo)
                .collect(Collectors.toList());
    }
}
