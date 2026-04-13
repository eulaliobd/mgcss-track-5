package com.mgcss.service;

import com.mgcss.domain.Solicitud;
import com.mgcss.domain.SolicitudRepository;
import com.mgcss.domain.Tecnico;
import com.mgcss.domain.TecnicoRepository;

public class SolicitudService {

	private final SolicitudRepository solicitudRepository;
    private final TecnicoRepository tecnicoRepository;
    
    public SolicitudService(SolicitudRepository solicitudRepository, TecnicoRepository tecnicoRepository) {
        this.solicitudRepository = solicitudRepository;
        this.tecnicoRepository = tecnicoRepository;
    }

    public void crearSolicitud(Solicitud solicitud) {
        solicitudRepository.save(solicitud);
    }

    public void asignarTecnico(Long solicitudId, Long tecnicoId) {
        Tecnico tecnico = tecnicoRepository.findById(tecnicoId).get();
        Solicitud solicitud = solicitudRepository.findById(solicitudId).get();
        
        solicitud.asignarTecnico(tecnico); 
        solicitudRepository.save(solicitud); 
    }
    
    public void cerrarSolicitud(Long solicitudId) {
        Solicitud solicitud = solicitudRepository.findById(solicitudId).get();
        
        solicitud.cerrar(); 
        
        solicitudRepository.save(solicitud);
    }
	
}
