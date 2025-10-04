package com.arrendamiento.notificaciones.service;

import com.arrendamiento.notificaciones.dto.NotificacionRequest;
import com.arrendamiento.notificaciones.entity.Notificacion;
import com.arrendamiento.notificaciones.entity.Notificacion.EstadoNotificacion;
import com.arrendamiento.notificaciones.repository.NotificacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificacionService {
    
    @Autowired
    private NotificacionRepository notificacionRepository;
    
    @Autowired(required = false)
    private JavaMailSender mailSender;
    
    public Notificacion crearNotificacion(NotificacionRequest request) {
        Notificacion notificacion = new Notificacion();
        notificacion.setDestinatario(request.getDestinatario());
        notificacion.setAsunto(request.getAsunto());
        notificacion.setMensaje(request.getMensaje());
        notificacion.setTipo(request.getTipo());
        notificacion.setContratoId(request.getContratoId());
        notificacion.setPagoId(request.getPagoId());
        notificacion.setEstado(EstadoNotificacion.PENDIENTE);
        
        Notificacion guardada = notificacionRepository.save(notificacion);
        
        // Intentar enviar inmediatamente
        procesarNotificacion(guardada);
        
        return guardada;
    }
    
    public void procesarNotificacion(Notificacion notificacion) {
        try {
            switch (notificacion.getTipo()) {
                case EMAIL:
                    enviarEmail(notificacion);
                    break;
                case SMS:
                    enviarSMS(notificacion);
                    break;
                case WHATSAPP:
                    enviarWhatsApp(notificacion);
                    break;
            }
            
            notificacion.setEstado(EstadoNotificacion.ENVIADO);
            notificacion.setFechaEnvio(LocalDateTime.now());
            notificacion.setErrorMensaje(null);
            
        } catch (Exception e) {
            notificacion.setIntentosEnvio(notificacion.getIntentosEnvio() + 1);
            notificacion.setErrorMensaje(e.getMessage());
            
            if (notificacion.getIntentosEnvio() >= 3) {
                notificacion.setEstado(EstadoNotificacion.FALLIDO);
            }
        }
        
        notificacionRepository.save(notificacion);
    }
    
    private void enviarEmail(Notificacion notificacion) {
        if (mailSender == null) {
            throw new RuntimeException("Servicio de correo no configurado");
        }
        
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(notificacion.getDestinatario());
        mensaje.setSubject(notificacion.getAsunto());
        mensaje.setText(notificacion.getMensaje());
        
        mailSender.send(mensaje);
    }
    
    private void enviarSMS(Notificacion notificacion) {
        // Implementación futura con API de SMS (Twilio, etc.)
        System.out.println("SMS enviado a: " + notificacion.getDestinatario());
        System.out.println("Mensaje: " + notificacion.getMensaje());
    }
    
    private void enviarWhatsApp(Notificacion notificacion) {
        // Implementación futura con WhatsApp Business API
        System.out.println("WhatsApp enviado a: " + notificacion.getDestinatario());
        System.out.println("Mensaje: " + notificacion.getMensaje());
    }
    
    @Scheduled(fixedRate = 300000) // Cada 5 minutos
    public void reintentarNotificacionesPendientes() {
        List<Notificacion> pendientes = notificacionRepository.findByEstado(EstadoNotificacion.PENDIENTE);
        
        for (Notificacion notificacion : pendientes) {
            if (notificacion.getIntentosEnvio() < 3) {
                procesarNotificacion(notificacion);
            }
        }
    }
    
    public List<Notificacion> listarNotificaciones() {
        return notificacionRepository.findAll();
    }
    
    public List<Notificacion> listarPorDestinatario(String destinatario) {
        return notificacionRepository.findByDestinatario(destinatario);
    }
    
    public List<Notificacion> listarPorContrato(Long contratoId) {
        return notificacionRepository.findByContratoId(contratoId);
    }
    
    public List<Notificacion> listarPorPago(Long pagoId) {
        return notificacionRepository.findByPagoId(pagoId);
    }
    
    public Notificacion obtenerPorId(Long id) {
        return notificacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Notificación no encontrada"));
    }
}

