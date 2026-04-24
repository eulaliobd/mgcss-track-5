package com.mgcss.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mgcss.domain.Solicitud;
import com.mgcss.domain.SolicitudRepository;

public interface JpaSolicitudRepository extends JpaRepository<Solicitud, Long>, SolicitudRepository {
}