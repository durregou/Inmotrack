package com.arrendamiento.mantenimiento.repository;

import com.arrendamiento.mantenimiento.entity.SolicitudMantenimiento;
import com.arrendamiento.mantenimiento.entity.SolicitudMantenimiento.EstadoMantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SolicitudMantenimientoRepository extends JpaRepository<SolicitudMantenimiento, Long> {
    
    List<SolicitudMantenimiento> findByInmuebleId(Long inmuebleId);
    
    List<SolicitudMantenimiento> findByContratoId(Long contratoId);
    
    List<SolicitudMantenimiento> findBySolicitanteId(Long solicitanteId);
    
    List<SolicitudMantenimiento> findByEstado(EstadoMantenimiento estado);
    
    List<SolicitudMantenimiento> findByTecnicoAsignado(String tecnico);
}

