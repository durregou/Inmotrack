package com.arrendamiento.mantenimiento.service;

import com.arrendamiento.mantenimiento.dto.MantenimientoRequest;
import com.arrendamiento.mantenimiento.entity.SolicitudMantenimiento;
import com.arrendamiento.mantenimiento.entity.SolicitudMantenimiento.EstadoMantenimiento;
import com.arrendamiento.mantenimiento.repository.SolicitudMantenimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MantenimientoService {
    
    @Autowired
    private SolicitudMantenimientoRepository solicitudRepository;
    
    public SolicitudMantenimiento crearSolicitud(MantenimientoRequest request) {
        SolicitudMantenimiento solicitud = new SolicitudMantenimiento();
        solicitud.setInmuebleId(request.getInmuebleId());
        solicitud.setContratoId(request.getContratoId());
        solicitud.setSolicitanteId(request.getSolicitanteId());
        solicitud.setTitulo(request.getTitulo());
        solicitud.setDescripcion(request.getDescripcion());
        solicitud.setTipo(request.getTipo());
        solicitud.setPrioridad(request.getPrioridad());
        solicitud.setTecnicoAsignado(request.getTecnicoAsignado());
        solicitud.setCostoEstimado(request.getCostoEstimado());
        solicitud.setObservaciones(request.getObservaciones());
        solicitud.setEstado(EstadoMantenimiento.PENDIENTE);
        
        return solicitudRepository.save(solicitud);
    }
    
    public SolicitudMantenimiento obtenerPorId(Long id) {
        return solicitudRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Solicitud de mantenimiento no encontrada"));
    }
    
    public List<SolicitudMantenimiento> listarTodas() {
        return solicitudRepository.findAll();
    }
    
    public List<SolicitudMantenimiento> listarPorInmueble(Long inmuebleId) {
        return solicitudRepository.findByInmuebleId(inmuebleId);
    }
    
    public List<SolicitudMantenimiento> listarPorContrato(Long contratoId) {
        return solicitudRepository.findByContratoId(contratoId);
    }
    
    public List<SolicitudMantenimiento> listarPorSolicitante(Long solicitanteId) {
        return solicitudRepository.findBySolicitanteId(solicitanteId);
    }
    
    public List<SolicitudMantenimiento> listarPorEstado(String estado) {
        EstadoMantenimiento estadoEnum = EstadoMantenimiento.valueOf(estado.toUpperCase());
        return solicitudRepository.findByEstado(estadoEnum);
    }
    
    public List<SolicitudMantenimiento> listarPorTecnico(String tecnico) {
        return solicitudRepository.findByTecnicoAsignado(tecnico);
    }
    
    public SolicitudMantenimiento aprobarSolicitud(Long id) {
        SolicitudMantenimiento solicitud = obtenerPorId(id);
        solicitud.setEstado(EstadoMantenimiento.APROBADO);
        return solicitudRepository.save(solicitud);
    }
    
    public SolicitudMantenimiento rechazarSolicitud(Long id, String motivo) {
        SolicitudMantenimiento solicitud = obtenerPorId(id);
        solicitud.setEstado(EstadoMantenimiento.RECHAZADO);
        solicitud.setObservaciones(motivo);
        return solicitudRepository.save(solicitud);
    }
    
    public SolicitudMantenimiento iniciarMantenimiento(Long id) {
        SolicitudMantenimiento solicitud = obtenerPorId(id);
        solicitud.setEstado(EstadoMantenimiento.EN_PROCESO);
        solicitud.setFechaInicio(LocalDateTime.now());
        return solicitudRepository.save(solicitud);
    }
    
    public SolicitudMantenimiento completarMantenimiento(Long id, Double costoReal, String observaciones) {
        SolicitudMantenimiento solicitud = obtenerPorId(id);
        solicitud.setEstado(EstadoMantenimiento.COMPLETADO);
        solicitud.setFechaFinalizacion(LocalDateTime.now());
        solicitud.setCostoReal(costoReal);
        if (observaciones != null) {
            solicitud.setObservaciones(observaciones);
        }
        return solicitudRepository.save(solicitud);
    }
    
    public SolicitudMantenimiento actualizarSolicitud(Long id, MantenimientoRequest request) {
        SolicitudMantenimiento solicitud = obtenerPorId(id);
        
        if (request.getTitulo() != null) {
            solicitud.setTitulo(request.getTitulo());
        }
        if (request.getDescripcion() != null) {
            solicitud.setDescripcion(request.getDescripcion());
        }
        if (request.getTipo() != null) {
            solicitud.setTipo(request.getTipo());
        }
        if (request.getPrioridad() != null) {
            solicitud.setPrioridad(request.getPrioridad());
        }
        if (request.getTecnicoAsignado() != null) {
            solicitud.setTecnicoAsignado(request.getTecnicoAsignado());
        }
        if (request.getCostoEstimado() != null) {
            solicitud.setCostoEstimado(request.getCostoEstimado());
        }
        if (request.getObservaciones() != null) {
            solicitud.setObservaciones(request.getObservaciones());
        }
        
        return solicitudRepository.save(solicitud);
    }
    
    public void eliminarSolicitud(Long id) {
        SolicitudMantenimiento solicitud = obtenerPorId(id);
        solicitudRepository.delete(solicitud);
    }
}

