package com.arrendamiento.notificaciones.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
public class Notificacion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String destinatario;
    
    @Column(nullable = false)
    private String asunto;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String mensaje;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoNotificacion tipo;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoNotificacion estado;
    
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;
    
    @Column(name = "intentos_envio")
    private Integer intentosEnvio = 0;
    
    @Column(name = "error_mensaje")
    private String errorMensaje;
    
    @Column(name = "contrato_id")
    private Long contratoId;
    
    @Column(name = "pago_id")
    private Long pagoId;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoNotificacion.PENDIENTE;
        }
    }
    
    // Enums
    public enum TipoNotificacion {
        EMAIL,
        SMS,
        WHATSAPP
    }
    
    public enum EstadoNotificacion {
        PENDIENTE,
        ENVIADO,
        FALLIDO,
        CANCELADO
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getDestinatario() {
        return destinatario;
    }
    
    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }
    
    public String getAsunto() {
        return asunto;
    }
    
    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
    public TipoNotificacion getTipo() {
        return tipo;
    }
    
    public void setTipo(TipoNotificacion tipo) {
        this.tipo = tipo;
    }
    
    public EstadoNotificacion getEstado() {
        return estado;
    }
    
    public void setEstado(EstadoNotificacion estado) {
        this.estado = estado;
    }
    
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    
    public LocalDateTime getFechaEnvio() {
        return fechaEnvio;
    }
    
    public void setFechaEnvio(LocalDateTime fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }
    
    public Integer getIntentosEnvio() {
        return intentosEnvio;
    }
    
    public void setIntentosEnvio(Integer intentosEnvio) {
        this.intentosEnvio = intentosEnvio;
    }
    
    public String getErrorMensaje() {
        return errorMensaje;
    }
    
    public void setErrorMensaje(String errorMensaje) {
        this.errorMensaje = errorMensaje;
    }
    
    public Long getContratoId() {
        return contratoId;
    }
    
    public void setContratoId(Long contratoId) {
        this.contratoId = contratoId;
    }
    
    public Long getPagoId() {
        return pagoId;
    }
    
    public void setPagoId(Long pagoId) {
        this.pagoId = pagoId;
    }
}

