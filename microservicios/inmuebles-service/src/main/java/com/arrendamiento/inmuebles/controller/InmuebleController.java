package com.arrendamiento.inmuebles.controller;

import com.arrendamiento.inmuebles.dto.InmuebleRequest;
import com.arrendamiento.inmuebles.entity.Inmueble;
import com.arrendamiento.inmuebles.service.InmuebleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inmuebles")
@CrossOrigin(origins = "*")
public class InmuebleController {

    @Autowired
    private InmuebleService inmuebleService;

    /**
     * POST /api/inmuebles → registrar inmueble
     */
    @PostMapping
    public ResponseEntity<?> registrarInmueble(@Valid @RequestBody InmuebleRequest request) {
        try {
            Inmueble inmueble = inmuebleService.registrarInmueble(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(inmueble);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error en el registro", e.getMessage()));
        }
    }

    /**
     * GET /api/inmuebles → listar inmuebles
     */
    @GetMapping
    public ResponseEntity<?> listarInmuebles(
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) String ciudad,
            @RequestParam(required = false) Long propietarioId,
            @RequestParam(required = false, defaultValue = "false") boolean todos) {
        try {
            List<Inmueble> inmuebles;
            
            if (propietarioId != null) {
                inmuebles = inmuebleService.obtenerPorPropietario(propietarioId);
            } else if (tipo != null) {
                inmuebles = inmuebleService.obtenerPorTipo(tipo);
            } else if (ciudad != null) {
                inmuebles = inmuebleService.obtenerPorCiudad(ciudad);
            } else if (todos) {
                inmuebles = inmuebleService.listarTodosLosInmuebles();
            } else {
                inmuebles = inmuebleService.listarInmuebles(); // Solo disponibles y activos
            }
            
            return ResponseEntity.ok(inmuebles);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno", e.getMessage()));
        }
    }

    /**
     * GET /api/inmuebles/{id} → obtener inmueble por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerInmueble(@PathVariable Long id) {
        try {
            Optional<Inmueble> inmueble = inmuebleService.obtenerPorId(id);
            
            if (inmueble.isPresent()) {
                return ResponseEntity.ok(inmueble.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno", e.getMessage()));
        }
    }

    /**
     * PUT /api/inmuebles/{id}/disponibilidad → actualizar disponibilidad
     */
    @PutMapping("/{id}/disponibilidad")
    public ResponseEntity<?> actualizarDisponibilidad(
            @PathVariable Long id,
            @RequestParam Boolean disponible) {
        try {
            Inmueble inmueble = inmuebleService.actualizarDisponibilidad(id, disponible);
            return ResponseEntity.ok(inmueble);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno", e.getMessage()));
        }
    }

    // Clase interna para respuestas de error
    public static class ErrorResponse {
        private String error;
        private String mensaje;

        public ErrorResponse(String error, String mensaje) {
            this.error = error;
            this.mensaje = mensaje;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public String getMensaje() {
            return mensaje;
        }

        public void setMensaje(String mensaje) {
            this.mensaje = mensaje;
        }
    }
}
