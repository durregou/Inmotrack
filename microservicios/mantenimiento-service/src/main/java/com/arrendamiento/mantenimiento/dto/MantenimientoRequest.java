package com.arrendamiento.mantenimiento.dto;

import com.arrendamiento.mantenimiento.entity.SolicitudMantenimiento.PrioridadMantenimiento;
import com.arrendamiento.mantenimiento.entity.SolicitudMantenimiento.TipoMantenimiento;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MantenimientoRequest {
    
    @NotNull(message = "El ID del inmueble es obligatorio")
    private Long inmuebleId;
    
    private Long contratoId;
    
    @NotNull(message = "El ID del solicitante es obligatorio")
    private Long solicitanteId;
    
    @NotBlank(message = "El título es obligatorio")
    private String titulo;
    
    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;
    
    @NotNull(message = "El tipo de mantenimiento es obligatorio")
    private TipoMantenimiento tipo;
    
    @NotNull(message = "La prioridad es obligatoria")
    private PrioridadMantenimiento prioridad;
    
    private String tecnicoAsignado;
    private Double costoEstimado;
    private String observaciones;
    
    // Getters y Setters
    public Long getInmuebleId() {
        return inmuebleId;
    }
    
    public void setInmuebleId(Long inmuebleId) {
        this.inmuebleId = inmuebleId;
    }
    
    public Long getContratoId() {
        return contratoId;
    }
    
    public void setContratoId(Long contratoId) {
        this.contratoId = contratoId;
    }
    
    public Long getSolicitanteId() {
        return solicitanteId;
    }
    
    public void setSolicitanteId(Long solicitanteId) {
        this.solicitanteId = solicitanteId;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getDescripcion() {
        return descripcion;
    }
    
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public TipoMantenimiento getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoMantenimiento tipo) {
        this.tipo = tipo;
    }
    
    public PrioridadMantenimiento getPrioridad() {
        return prioridad;
    }
    
    public void setPrioridad(PrioridadMantenimiento prioridad) {
        this.prioridad = prioridad;
    }
    
    public String getTecnicoAsignado() {
        return tecnicoAsignado;
    }
    
    public void setTecnicoAsignado(String tecnicoAsignado) {
        this.tecnicoAsignado = tecnicoAsignado;
    }
    
    public Double getCostoEstimado() {
        return costoEstimado;
    }
    
    public void setCostoEstimado(Double costoEstimado) {
        this.costoEstimado = costoEstimado;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}

