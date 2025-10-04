package com.arrendamiento.mantenimiento.controller;

import com.arrendamiento.mantenimiento.dto.MantenimientoRequest;
import com.arrendamiento.mantenimiento.entity.SolicitudMantenimiento;
import com.arrendamiento.mantenimiento.service.MantenimientoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/mantenimiento")
@CrossOrigin(origins = "*")
public class MantenimientoController {
    
    @Autowired
    private MantenimientoService mantenimientoService;
    
    @PostMapping
    public ResponseEntity<?> crearSolicitud(@Valid @RequestBody MantenimientoRequest request) {
        try {
            SolicitudMantenimiento solicitud = mantenimientoService.crearSolicitud(request);
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Solicitud de mantenimiento creada exitosamente");
            response.put("solicitud", solicitud);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<SolicitudMantenimiento>> listarSolicitudes(
            @RequestParam(required = false) Long inmuebleId,
            @RequestParam(required = false) Long contratoId,
            @RequestParam(required = false) Long solicitanteId,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String tecnico) {
        
        if (inmuebleId != null) {
            return ResponseEntity.ok(mantenimientoService.listarPorInmueble(inmuebleId));
        }
        
        if (contratoId != null) {
            return ResponseEntity.ok(mantenimientoService.listarPorContrato(contratoId));
        }
        
        if (solicitanteId != null) {
            return ResponseEntity.ok(mantenimientoService.listarPorSolicitante(solicitanteId));
        }
        
        if (estado != null) {
            return ResponseEntity.ok(mantenimientoService.listarPorEstado(estado));
        }
        
        if (tecnico != null) {
            return ResponseEntity.ok(mantenimientoService.listarPorTecnico(tecnico));
        }
        
        return ResponseEntity.ok(mantenimientoService.listarTodas());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerSolicitud(@PathVariable Long id) {
        try {
            SolicitudMantenimiento solicitud = mantenimientoService.obtenerPorId(id);
            return ResponseEntity.ok(solicitud);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarSolicitud(@PathVariable Long id, @RequestBody MantenimientoRequest request) {
        try {
            SolicitudMantenimiento solicitud = mantenimientoService.actualizarSolicitud(id, request);
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Solicitud actualizada exitosamente");
            response.put("solicitud", solicitud);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @PutMapping("/{id}/aprobar")
    public ResponseEntity<?> aprobarSolicitud(@PathVariable Long id) {
        try {
            SolicitudMantenimiento solicitud = mantenimientoService.aprobarSolicitud(id);
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Solicitud aprobada exitosamente");
            response.put("solicitud", solicitud);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @PutMapping("/{id}/rechazar")
    public ResponseEntity<?> rechazarSolicitud(@PathVariable Long id, @RequestParam String motivo) {
        try {
            SolicitudMantenimiento solicitud = mantenimientoService.rechazarSolicitud(id, motivo);
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Solicitud rechazada exitosamente");
            response.put("solicitud", solicitud);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @PutMapping("/{id}/iniciar")
    public ResponseEntity<?> iniciarMantenimiento(@PathVariable Long id) {
        try {
            SolicitudMantenimiento solicitud = mantenimientoService.iniciarMantenimiento(id);
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Mantenimiento iniciado exitosamente");
            response.put("solicitud", solicitud);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @PutMapping("/{id}/completar")
    public ResponseEntity<?> completarMantenimiento(
            @PathVariable Long id,
            @RequestParam Double costoReal,
            @RequestParam(required = false) String observaciones) {
        try {
            SolicitudMantenimiento solicitud = mantenimientoService.completarMantenimiento(id, costoReal, observaciones);
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Mantenimiento completado exitosamente");
            response.put("solicitud", solicitud);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarSolicitud(@PathVariable Long id) {
        try {
            mantenimientoService.eliminarSolicitud(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", "Solicitud eliminada exitosamente");
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}

