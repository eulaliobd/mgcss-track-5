package com.mgcss.service;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.TecnicoRepository;
import com.mgcss.domain.Estado;
import com.mgcss.domain.Solicitud;
import com.mgcss.domain.SolicitudRepository;
import com.mgcss.domain.Tecnico;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class SolicitudServiceTest {

	@Test
    void LanzarExcepcionSiTecnicoInactivo() {
        SolicitudRepository solicitudRepoMock = mock(SolicitudRepository.class);
        TecnicoRepository tecnicoRepoMock = mock(TecnicoRepository.class);
        SolicitudService service = new SolicitudService(solicitudRepoMock, tecnicoRepoMock);
        
        Tecnico tecnicoInactivo = new Tecnico();
        tecnicoInactivo.setActivo(false);
        
        Solicitud solicitud = new Solicitud(); 
        
        when(tecnicoRepoMock.findById(1L)).thenReturn(Optional.of(tecnicoInactivo));
        when(solicitudRepoMock.findById(100L)).thenReturn(Optional.of(solicitud)); 
        
        assertThrows(IllegalArgumentException.class, () -> {
            service.asignarTecnico(100L, 1L);
        });
        
        verify(solicitudRepoMock, never()).save(any(Solicitud.class));
    }

    @Test
    void AsignarTecnicoCorrectamente() {
        SolicitudRepository solicitudRepoMock = mock(SolicitudRepository.class);
        TecnicoRepository tecnicoRepoMock = mock(TecnicoRepository.class);
        SolicitudService service = new SolicitudService(solicitudRepoMock, tecnicoRepoMock);
        
        Tecnico tecnicoActivo = new Tecnico();
        tecnicoActivo.setActivo(true);
        
        Solicitud solicitud = new Solicitud();
        solicitud.setEstado(Estado.EN_PROCESO); 
        
        when(tecnicoRepoMock.findById(1L)).thenReturn(Optional.of(tecnicoActivo));
        when(solicitudRepoMock.findById(100L)).thenReturn(Optional.of(solicitud));
        
        service.asignarTecnico(100L, 1L);
        
        verify(solicitudRepoMock).save(any(Solicitud.class));
    }

    @Test
    void CrearSolicitudLlamandoAlRepositorio() {
        SolicitudRepository solicitudRepoMock = mock(SolicitudRepository.class);
        TecnicoRepository tecnicoRepoMock = mock(TecnicoRepository.class);
        SolicitudService service = new SolicitudService(solicitudRepoMock, tecnicoRepoMock);
        
        Solicitud nuevaSolicitud = new Solicitud();
        
        service.crearSolicitud(nuevaSolicitud);
        
        verify(solicitudRepoMock).save(nuevaSolicitud);
    }
    
    @Test
    void deberiaCerrarSolicitudLlamandoAlRepositorio() {
        SolicitudRepository solicitudRepoMock = mock(SolicitudRepository.class);
        TecnicoRepository tecnicoRepoMock = mock(TecnicoRepository.class);
        SolicitudService service = new SolicitudService(solicitudRepoMock, tecnicoRepoMock);
        
        Tecnico tecnico = new Tecnico();
        tecnico.setActivo(true);
        
        Solicitud solicitud = new Solicitud();
        solicitud.setEstado(Estado.EN_PROCESO); 
        solicitud.asignarTecnico(tecnico); 
        
        when(solicitudRepoMock.findById(100L)).thenReturn(Optional.of(solicitud));
        
        service.cerrarSolicitud(100L); 
        
        verify(solicitudRepoMock).save(solicitud);
    }
    
    @Test
    void LanzarExcepcionSiBuscarPorIdNoExiste() {
        SolicitudRepository solicitudRepoMock = mock(SolicitudRepository.class);
        TecnicoRepository tecnicoRepoMock = mock(TecnicoRepository.class);
        SolicitudService service = new SolicitudService(solicitudRepoMock, tecnicoRepoMock);
        
        when(solicitudRepoMock.findById(99L)).thenReturn(Optional.empty());
        
        assertThrows(IllegalArgumentException.class, () -> {
            service.buscarPorId(99L);
        });
    }

    @Test
    void LanzarExcepcionSiCerrarSolicitudNoExiste() {
        SolicitudRepository solicitudRepoMock = mock(SolicitudRepository.class);
        TecnicoRepository tecnicoRepoMock = mock(TecnicoRepository.class);
        SolicitudService service = new SolicitudService(solicitudRepoMock, tecnicoRepoMock);
        
        when(solicitudRepoMock.findById(99L)).thenReturn(Optional.empty());
        
        assertThrows(IllegalArgumentException.class, () -> {
            service.cerrarSolicitud(99L);
        });
    }

    @Test
    void LanzarExcepcionSiAsignarTecnicoYTecnicoNoExiste() {
        SolicitudRepository solicitudRepoMock = mock(SolicitudRepository.class);
        TecnicoRepository tecnicoRepoMock = mock(TecnicoRepository.class);
        SolicitudService service = new SolicitudService(solicitudRepoMock, tecnicoRepoMock);
        
        when(tecnicoRepoMock.findById(99L)).thenReturn(Optional.empty());
        
        assertThrows(IllegalArgumentException.class, () -> {
            service.asignarTecnico(1L, 99L); 
        });
        
        verify(solicitudRepoMock, never()).save(any(Solicitud.class));
    }

    @Test
    void LanzarExcepcionSiAsignarTecnicoYSolicitudNoExiste() {
        SolicitudRepository solicitudRepoMock = mock(SolicitudRepository.class);
        TecnicoRepository tecnicoRepoMock = mock(TecnicoRepository.class);
        SolicitudService service = new SolicitudService(solicitudRepoMock, tecnicoRepoMock);
        
        Tecnico tecnico = new Tecnico();
        when(tecnicoRepoMock.findById(1L)).thenReturn(Optional.of(tecnico));
        
        when(solicitudRepoMock.findById(99L)).thenReturn(Optional.empty());
        
        assertThrows(IllegalArgumentException.class, () -> {
            service.asignarTecnico(99L, 1L); // ID Solicitud: 99, ID Técnico: 1
        });
    }
    
}