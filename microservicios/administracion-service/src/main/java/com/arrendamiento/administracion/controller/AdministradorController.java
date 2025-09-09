package com.arrendamiento.administracion.controller;

import com.arrendamiento.administracion.dto.LoginRequest;
import com.arrendamiento.administracion.dto.LoginResponse;
import com.arrendamiento.administracion.entity.Administrador;
import com.arrendamiento.administracion.service.AdministradorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdministradorController {

    @Autowired
    private AdministradorService administradorService;

    /**
     * POST /api/admin/login → autenticación
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = administradorService.login(loginRequest);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponse("Error de autenticación", e.getMessage()));
        }
    }

    /**
     * GET /api/admin/{id} → consulta de datos del administrador
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getAdministrador(@PathVariable Long id) {
        try {
            Optional<Administrador> administrador = administradorService.findById(id);
            
            if (administrador.isPresent()) {
                Administrador admin = administrador.get();
                // No retornar la contraseña por seguridad
                admin.setContrasena(null);
                return ResponseEntity.ok(admin);
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
