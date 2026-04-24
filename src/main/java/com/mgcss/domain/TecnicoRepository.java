package com.mgcss.domain;

import java.util.List;
import java.util.Optional;

public interface TecnicoRepository {
	Optional<Tecnico> findById(Long id); 
	List<Tecnico> findAll();
	Tecnico save(Tecnico tecnico);
}
