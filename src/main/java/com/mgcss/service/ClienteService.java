package com.mgcss.service;

import com.mgcss.domain.Cliente;
import com.mgcss.domain.ClienteRepository;
import com.mgcss.domain.TipoCliente;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    public Cliente crearCliente(String nombre, String email, TipoCliente tipo) {
        Cliente cliente = new Cliente(nombre, email, tipo);
        return clienteRepository.save(cliente);
    }

    public Cliente modificarDatosBasicos(Long id, String nuevoNombre, String nuevoEmail) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        
        cliente.setNombre(nuevoNombre);
        cliente.setEmail(nuevoEmail);
        
        return clienteRepository.save(cliente);
    }

    public Cliente consultarCliente(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
    }

    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }
}