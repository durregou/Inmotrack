package com.arrendamiento.notificaciones.controller;

import com.arrendamiento.notificaciones.dto.NotificacionRequest;
import com.arrendamiento.notificaciones.entity.Notificacion;
import com.arrendamiento.notificaciones.service.NotificacionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notificaciones")
@CrossOrigin(origins = "*")
public class NotificacionController {
    
    @Autowired
    private NotificacionService notificacionService;
    
    @PostMapping
    public ResponseEntity<?> crearNotificacion(@Valid @RequestBody NotificacionRequest request) {
        try {
            Notificacion notificacion = notificacionService.crearNotificacion(request);
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Notificación creada exitosamente");
            response.put("notificacion", notificacion);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Notificacion>> listarNotificaciones(
            @RequestParam(required = false) String destinatario,
            @RequestParam(required = false) Long contratoId,
            @RequestParam(required = false) Long pagoId) {
        
        if (destinatario != null) {
            return ResponseEntity.ok(notificacionService.listarPorDestinatario(destinatario));
        }
        
        if (contratoId != null) {
            return ResponseEntity.ok(notificacionService.listarPorContrato(contratoId));
        }
        
        if (pagoId != null) {
            return ResponseEntity.ok(notificacionService.listarPorPago(pagoId));
        }
        
        return ResponseEntity.ok(notificacionService.listarNotificaciones());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerNotificacion(@PathVariable Long id) {
        try {
            Notificacion notificacion = notificacionService.obtenerPorId(id);
            return ResponseEntity.ok(notificacion);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @PostMapping("/enviar-pago-pendiente")
    public ResponseEntity<?> enviarNotificacionPagoPendiente(
            @RequestParam String correo,
            @RequestParam Long contratoId,
            @RequestParam Long pagoId,
            @RequestParam Double monto) {
        
        try {
            NotificacionRequest request = new NotificacionRequest();
            request.setDestinatario(correo);
            request.setAsunto("Pago Pendiente - Sistema de Arrendamiento");
            request.setMensaje(String.format(
                "Estimado usuario,\n\n" +
                "Le recordamos que tiene un pago pendiente:\n" +
                "Contrato ID: %d\n" +
                "Pago ID: %d\n" +
                "Monto: $%.2f\n\n" +
                "Por favor, realice el pago a la brevedad posible.\n\n" +
                "Gracias.",
                contratoId, pagoId, monto
            ));
            request.setTipo(Notificacion.TipoNotificacion.EMAIL);
            request.setContratoId(contratoId);
            request.setPagoId(pagoId);
            
            Notificacion notificacion = notificacionService.crearNotificacion(request);
            
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Notificación de pago pendiente enviada");
            response.put("notificacion", notificacion);
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
}

