package com.arrendamiento.pagos.controller;

import com.arrendamiento.pagos.dto.PagoRequest;
import com.arrendamiento.pagos.entity.Pago;
import com.arrendamiento.pagos.service.PagoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pagos")
@CrossOrigin(origins = "*")
public class PagoController {

    @Autowired
    private PagoService pagoService;

    /**
     * POST /api/pagos → registrar pago
     */
    @PostMapping
    public ResponseEntity<?> registrarPago(@Valid @RequestBody PagoRequest request) {
        try {
            Pago pago = pagoService.registrarPago(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(pago);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Error en el registro del pago", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno", "Error inesperado en el servidor"));
        }
    }

    /**
     * GET /api/pagos → listar pagos con filtros
     * GET /api/pagos?contrato={id} → historial de pagos por contrato
     */
    @GetMapping
    public ResponseEntity<?> listarPagos(
            @RequestParam(required = false) Long contrato,
            @RequestParam(required = false) Long arrendatario,
            @RequestParam(required = false) String estado,
            @RequestParam(required = false) String tipo,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false, defaultValue = "false") boolean vencidos,
            @RequestParam(required = false, defaultValue = "0") int porVencerEnDias) {
        try {
            List<Pago> pagos;
            
            if (contrato != null) {
                pagos = pagoService.obtenerHistorialPorContrato(contrato);
            } else if (arrendatario != null) {
                pagos = pagoService.obtenerPorArrendatario(arrendatario);
            } else if (estado != null) {
                Pago.EstadoPago estadoPago = Pago.EstadoPago.valueOf(estado.toUpperCase());
                pagos = List.of(); // Necesitaríamos implementar este método
                if (estadoPago == Pago.EstadoPago.PENDIENTE) {
                    pagos = pagoService.obtenerPagosPendientes();
                }
            } else if (tipo != null) {
                Pago.TipoPago tipoPago = Pago.TipoPago.valueOf(tipo.toUpperCase());
                pagos = pagoService.obtenerPorTipo(tipoPago);
            } else if (year != null && month != null) {
                pagos = pagoService.obtenerPagosPorMes(year, month);
            } else if (vencidos) {
                pagos = pagoService.obtenerPagosVencidos();
            } else if (porVencerEnDias > 0) {
                pagos = pagoService.obtenerPagosPorVencer(porVencerEnDias);
            } else {
                pagos = pagoService.obtenerPagosPendientes(); // Por defecto, pagos pendientes
            }
            
            return ResponseEntity.ok(pagos);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno", e.getMessage()));
        }
    }

    /**
     * GET /api/pagos/{id} → obtener pago por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPago(@PathVariable Long id) {
        try {
            Optional<Pago> pago = pagoService.obtenerPorId(id);
            
            if (pago.isPresent()) {
                return ResponseEntity.ok(pago.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno", e.getMessage()));
        }
    }

    /**
     * PUT /api/pagos/{id}/estado → actualizar estado del pago
     */
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstado(
            @PathVariable Long id,
            @RequestParam String estado) {
        try {
            Pago.EstadoPago nuevoEstado = Pago.EstadoPago.valueOf(estado.toUpperCase());
            Pago pago = pagoService.actualizarEstadoPago(id, nuevoEstado);
            return ResponseEntity.ok(pago);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse("Estado inválido", "Los estados válidos son: PENDIENTE, PAGADO, VENCIDO, PARCIAL"));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno", e.getMessage()));
        }
    }

    /**
     * GET /api/pagos/contrato/{contratoId}/pendientes/count → contar pagos pendientes
     */
    @GetMapping("/contrato/{contratoId}/pendientes/count")
    public ResponseEntity<?> contarPagosPendientes(@PathVariable Long contratoId) {
        try {
            Long count = pagoService.contarPagosPendientesPorContrato(contratoId);
            return ResponseEntity.ok(new CountResponse(count));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error interno", e.getMessage()));
        }
    }

    // Clases internas para respuestas
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

    public static class CountResponse {
        private Long count;

        public CountResponse(Long count) {
            this.count = count;
        }

        public Long getCount() {
            return count;
        }

        public void setCount(Long count) {
            this.count = count;
        }
    }
}
