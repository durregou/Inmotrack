package com.arrendamiento.notificaciones.dto;

import com.arrendamiento.notificaciones.entity.Notificacion.TipoNotificacion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NotificacionRequest {
    
    @NotBlank(message = "El destinatario es obligatorio")
    private String destinatario;
    
    @NotBlank(message = "El asunto es obligatorio")
    private String asunto;
    
    @NotBlank(message = "El mensaje es obligatorio")
    private String mensaje;
    
    @NotNull(message = "El tipo de notificación es obligatorio")
    private TipoNotificacion tipo;
    
    private Long contratoId;
    private Long pagoId;
    
    // Getters y Setters
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

