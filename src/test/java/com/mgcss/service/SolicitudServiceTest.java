package com.mgcss.service;

import org.junit.jupiter.api.Test;

import com.mgcss.domain.TecnicoRepository;
import com.mgcss.domain.Cliente;
import com.mgcss.domain.ClienteRepository;
import com.mgcss.domain.Estado;
import com.mgcss.domain.Solicitud;
import com.mgcss.domain.SolicitudRepository;
import com.mgcss.domain.Tecnico;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SolicitudServiceTest {

	@Test
    void LanzarExcepcionSiTecnicoInactivo() {
        SolicitudRepository solicitudRepoMock = mock(SolicitudRepository.class);
        TecnicoRepository tecnicoRepoMock = mock(TecnicoRepository.class);
        ClienteRepository clienteRepoMock = mock(ClienteRepository.class);
        SolicitudService service = new SolicitudService(solicitudRepoMock, tecnicoRepoMock, clienteRepoMock);
        
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
        ClienteRepository clienteRepoMock = mock(ClienteRepository.class);
        SolicitudService service = new SolicitudService(solicitudRepoMock, tecnicoRepoMock, clienteRepoMock);
        
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
        ClienteRepository clienteRepoMock = mock(ClienteRepository.class);
        SolicitudService service = new SolicitudService(solicitudRepoMock, tecnicoRepoMock, clienteRepoMock);        
        Solicitud nuevaSolicitud = new Solicitud();
        
        service.crearSolicitud(nuevaSolicitud);
        
        verify(solicitudRepoMock).save(nuevaSolicitud);
    }
    
    @Test
    void deberiaCerrarSolicitudLlamandoAlRepositorio() {
        SolicitudRepository solicitudRepoMock = mock(SolicitudRepository.class);
        TecnicoRepository tecnicoRepoMock = mock(TecnicoRepository.class);
        ClienteRepository clienteRepoMock = mock(ClienteRepository.class);
        SolicitudService service = new SolicitudService(solicitudRepoMock, tecnicoRepoMock, clienteRepoMock);        
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
        ClienteRepository clienteRepoMock = mock(ClienteRepository.class);
        SolicitudService service = new SolicitudService(solicitudRepoMock, tecnicoRepoMock, clienteRepoMock);        
        when(solicitudRepoMock.findById(99L)).thenReturn(Optional.empty());
        
        assertThrows(IllegalArgumentException.class, () -> {
            service.buscarPorId(99L);
        });
    }

    @Test
    void LanzarExcepcionSiCerrarSolicitudNoExiste() {
        SolicitudRepository solicitudRepoMock = mock(SolicitudRepository.class);
        TecnicoRepository tecnicoRepoMock = mock(TecnicoRepository.class);
        ClienteRepository clienteRepoMock = mock(ClienteRepository.class);
        SolicitudService service = new SolicitudService(solicitudRepoMock, tecnicoRepoMock, clienteRepoMock);        
        when(solicitudRepoMock.findById(99L)).thenReturn(Optional.empty());
        
        assertThrows(IllegalArgumentException.class, () -> {
            service.cerrarSolicitud(99L);
        });
    }

    @Test
    void LanzarExcepcionSiAsignarTecnicoYTecnicoNoExiste() {
        SolicitudRepository solicitudRepoMock = mock(SolicitudRepository.class);
        TecnicoRepository tecnicoRepoMock = mock(TecnicoRepository.class);
        ClienteRepository clienteRepoMock = mock(ClienteRepository.class);
        SolicitudService service = new SolicitudService(solicitudRepoMock, tecnicoRepoMock, clienteRepoMock);        
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
        ClienteRepository clienteRepoMock = mock(ClienteRepository.class);
        SolicitudService service = new SolicitudService(solicitudRepoMock, tecnicoRepoMock, clienteRepoMock);        
        Tecnico tecnico = new Tecnico();
        when(tecnicoRepoMock.findById(1L)).thenReturn(Optional.of(tecnico));
        
        when(solicitudRepoMock.findById(99L)).thenReturn(Optional.empty());
        
        assertThrows(IllegalArgumentException.class, () -> {
            service.asignarTecnico(99L, 1L); // ID Solicitud: 99, ID Técnico: 1
        });
    }
    
    @Test
    void deberiaAsignarClienteASolicitud() {
        // Arrange
    	SolicitudRepository solicitudRepoMock = mock(SolicitudRepository.class);
        TecnicoRepository tecnicoRepoMock = mock(TecnicoRepository.class);
        ClienteRepository clienteRepoMock = mock(ClienteRepository.class);
        SolicitudService service = new SolicitudService(solicitudRepoMock, tecnicoRepoMock, clienteRepoMock);        
            	
        Solicitud solicitud = new Solicitud();
        solicitud.setId(1L);
        
        Cliente cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Paco");

        when(solicitudRepoMock.findById(1L)).thenReturn(Optional.of(solicitud));
        when(clienteRepoMock.findById(1L)).thenReturn(Optional.of(cliente));
        when(solicitudRepoMock.save(any(Solicitud.class))).thenAnswer(i -> i.getArguments()[0]);
        
        // Act
        Solicitud actualizada = service.asignarCliente(1L, 1L);

        // Assert
        assertNotNull(actualizada.getCliente());
        assertEquals("Paco", actualizada.getCliente().getNombre());
    }
    
}