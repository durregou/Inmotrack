package com.arrendamiento.propietarios.controller;

import com.arrendamiento.propietarios.dto.PropietarioRequest;
import com.arrendamiento.propietarios.dto.PropietarioResponse;
import com.arrendamiento.propietarios.service.PropietarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/propietarios")
@CrossOrigin(origins = "*")
public class PropietarioController {

    @Autowired
    private PropietarioService propietarioService;

    /**
     * POST /api/propietarios → registrar propietario
     */
    @PostMapping
    public ResponseEntity<?> registrarPropietario(@Valid @RequestBody PropietarioRequest request) {
        try {
            PropietarioResponse response = propietarioService.registrarPropietario(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error en el registro", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno", "Error inesperado en el servidor"));
        }
    }

    /**
     * GET /api/propietarios/{id} → obtener información
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPropietario(@PathVariable Long id) {
        try {
            Optional<PropietarioResponse> propietario = propietarioService.obtenerPorId(id);
            
            if (propietario.isPresent()) {
                return ResponseEntity.ok(propietario.get());
            } else {
                return ResponseEntity.notFound().build();
            }
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
