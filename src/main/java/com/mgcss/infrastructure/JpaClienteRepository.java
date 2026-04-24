package com.mgcss.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mgcss.domain.Cliente;
import com.mgcss.domain.ClienteRepository;

public interface JpaClienteRepository extends JpaRepository<Cliente, Long>, ClienteRepository {
}