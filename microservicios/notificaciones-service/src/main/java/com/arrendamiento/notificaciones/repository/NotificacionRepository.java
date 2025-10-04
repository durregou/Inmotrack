package com.arrendamiento.notificaciones.repository;

import com.arrendamiento.notificaciones.entity.Notificacion;
import com.arrendamiento.notificaciones.entity.Notificacion.EstadoNotificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    
    List<Notificacion> findByEstado(EstadoNotificacion estado);
    
    List<Notificacion> findByDestinatario(String destinatario);
    
    List<Notificacion> findByContratoId(Long contratoId);
    
    List<Notificacion> findByPagoId(Long pagoId);
    
    List<Notificacion> findByEstadoAndFechaCreacionBefore(EstadoNotificacion estado, LocalDateTime fecha);
}

