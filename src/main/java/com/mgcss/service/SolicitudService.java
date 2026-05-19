package com.mgcss.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mgcss.domain.Cliente;
import com.mgcss.domain.ClienteRepository;
import com.mgcss.domain.Solicitud;
import com.mgcss.domain.SolicitudRepository;
import com.mgcss.domain.Tecnico;
import com.mgcss.domain.TecnicoRepository;

@Service
public class SolicitudService {

	private final SolicitudRepository solicitudRepository;
    private final TecnicoRepository tecnicoRepository;
    private final ClienteRepository clienteRepository; 
    
    public SolicitudService(SolicitudRepository solicitudRepository, TecnicoRepository tecnicoRepository, ClienteRepository clienteRepository) {
        this.solicitudRepository = solicitudRepository;
        this.tecnicoRepository = tecnicoRepository;
        this.clienteRepository = clienteRepository;
    }

    public void crearSolicitud(Solicitud solicitud) {
        solicitudRepository.save(solicitud);
    }

    public void asignarTecnico(Long solicitudId, Long tecnicoId) {
    	Tecnico tecnico = tecnicoRepository.findById(tecnicoId)
    		    .orElseThrow(() -> new IllegalArgumentException("El técnico especificado no existe"));
    		        
    		Solicitud solicitud = solicitudRepository.findById(solicitudId)
    		    .orElseThrow(() -> new IllegalArgumentException("La solicitud especificada no existe"));
        
        solicitud.asignarTecnico(tecnico); 
        solicitudRepository.save(solicitud); 
    }
    
    public void cerrarSolicitud(Long solicitudId) {
    	Solicitud solicitud = solicitudRepository.findById(solicitudId)
    		    .orElseThrow(() -> new IllegalArgumentException("La solicitud especificada no existe"));
        
        solicitud.cerrar(); 
        
        solicitudRepository.save(solicitud);
    }
    
    public Solicitud buscarPorId(Long id) {
        return solicitudRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("La solicitud con ID " + id + " no existe"));
    }

    
    public List<Solicitud> listarTodas() {
        return solicitudRepository.findAll();
    }

    public void reabrirSolicitud(Long id) {
        Solicitud solicitud = buscarPorId(id);
        
        solicitud.reabrir(); 
        
        solicitudRepository.save(solicitud);
    }
	
    public Solicitud asignarCliente(Long solicitudId, Long clienteId) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId)
                .orElseThrow(() -> new IllegalArgumentException("Solicitud no encontrada"));

        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

        solicitud.setCliente(cliente);
        return solicitudRepository.save(solicitud);
    }
}
