package com.mgcss.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mgcss.domain.Tecnico;
import com.mgcss.domain.TecnicoRepository;

public interface JpaTecnicoRepository extends JpaRepository<Tecnico, Long>, TecnicoRepository {
}