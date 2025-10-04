package com.arrendamiento.mantenimiento.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "solicitudes_mantenimiento")
public class SolicitudMantenimiento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "inmueble_id", nullable = false)
    private Long inmuebleId;
    
    @Column(name = "contrato_id")
    private Long contratoId;
    
    @Column(name = "solicitante_id", nullable = false)
    private Long solicitanteId;
    
    @Column(nullable = false)
    private String titulo;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoMantenimiento tipo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PrioridadMantenimiento prioridad;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMantenimiento estado;
    
    @Column(name = "fecha_solicitud")
    private LocalDateTime fechaSolicitud;
    
    @Column(name = "fecha_inicio")
    private LocalDateTime fechaInicio;
    
    @Column(name = "fecha_finalizacion")
    private LocalDateTime fechaFinalizacion;
    
    @Column(name = "tecnico_asignado")
    private String tecnicoAsignado;
    
    @Column(name = "costo_estimado")
    private Double costoEstimado;
    
    @Column(name = "costo_real")
    private Double costoReal;
    
    @Column(columnDefinition = "TEXT")
    private String observaciones;
    
    @PrePersist
    protected void onCreate() {
        fechaSolicitud = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoMantenimiento.PENDIENTE;
        }
    }
    
    // Enums
    public enum TipoMantenimiento {
        PREVENTIVO,
        CORRECTIVO,
        EMERGENCIA
    }
    
    public enum PrioridadMantenimiento {
        BAJA,
        MEDIA,
        ALTA,
        URGENTE
    }
    
    public enum EstadoMantenimiento {
        PENDIENTE,
        APROBADO,
        EN_PROCESO,
        COMPLETADO,
        RECHAZADO,
        CANCELADO
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public EstadoMantenimiento getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoMantenimiento estado) {
        this.estado = estado;
    }
    
    public LocalDateTime getFechaSolicitud() {
        return fechaSolicitud;
    }
    
    public void setFechaSolicitud(LocalDateTime fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
    
    public LocalDateTime getFechaInicio() {
        return fechaInicio;
    }
    
    public void setFechaInicio(LocalDateTime fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    
    public LocalDateTime getFechaFinalizacion() {
        return fechaFinalizacion;
    }
    
    public void setFechaFinalizacion(LocalDateTime fechaFinalizacion) {
        this.fechaFinalizacion = fechaFinalizacion;
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
    
    public Double getCostoReal() {
        return costoReal;
    }
    
    public void setCostoReal(Double costoReal) {
        this.costoReal = costoReal;
    }
    
    public String getObservaciones() {
        return observaciones;
    }
    
    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}

