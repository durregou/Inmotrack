package com.arrendamiento.contratos.controller;

import com.arrendamiento.contratos.dto.ContratoRequest;
import com.arrendamiento.contratos.entity.Contrato;
import com.arrendamiento.contratos.service.ContratoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contratos")
@CrossOrigin(origins = "*")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    /**
     * POST /api/contratos → crear contrato
     */
    @PostMapping
    public ResponseEntity<?> crearContrato(@Valid @RequestBody ContratoRequest request) {
        try {
            Contrato contrato = contratoService.crearContrato(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(contrato);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error en la creación del contrato", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno", "Error inesperado en el servidor"));
        }
    }

    /**
     * GET /api/contratos/{id} → consultar contrato
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerContrato(@PathVariable Long id) {
        try {
            Optional<Contrato> contrato = contratoService.obtenerPorId(id);
            
            if (contrato.isPresent()) {
                return ResponseEntity.ok(contrato.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno", e.getMessage()));
        }
    }

    /**
     * GET /api/contratos → listar contratos con filtros
     */
    @GetMapping
    public ResponseEntity<?> listarContratos(
            @RequestParam(required = false) Long propietarioId,
            @RequestParam(required = false) Long arrendatarioId,
            @RequestParam(required = false) Long inmuebleId,
            @RequestParam(required = false, defaultValue = "false") boolean soloActivos) {
        try {
            List<Contrato> contratos;
            
            if (propietarioId != null) {
                contratos = contratoService.obtenerPorPropietario(propietarioId);
            } else if (arrendatarioId != null) {
                contratos = contratoService.obtenerPorArrendatario(arrendatarioId);
            } else if (inmuebleId != null) {
                contratos = contratoService.obtenerPorInmueble(inmuebleId);
            } else if (soloActivos) {
                contratos = contratoService.obtenerActivos();
            } else {
                contratos = contratoService.obtenerActivos(); // Por defecto solo activos
            }
            
            return ResponseEntity.ok(contratos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno", e.getMessage()));
        }
    }

    /**
     * PUT /api/contratos/{id}/finalizar → finalizar contrato
     */
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<?> finalizarContrato(@PathVariable Long id) {
        try {
            Contrato contrato = contratoService.finalizarContrato(id);
            return ResponseEntity.ok(contrato);
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
