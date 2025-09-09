package com.arrendamiento.pagos.service;

import com.arrendamiento.pagos.dto.PagoRequest;
import com.arrendamiento.pagos.entity.Pago;
import com.arrendamiento.pagos.repository.PagoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${contratos.service.url:http://localhost:8084}")
    private String contratosServiceUrl;

    public Pago registrarPago(PagoRequest request) {
        // Validar que el contrato existe
        Boolean contratoExiste = validarContratoExiste(request.getContratoId());
        if (!contratoExiste) {
            throw new RuntimeException("El contrato especificado no existe");
        }

        // Crear el pago
        Pago pago = new Pago();
        pago.setContratoId(request.getContratoId());
        pago.setArrendatarioId(request.getArrendatarioId());
        pago.setValor(request.getValor());
        pago.setFecha(request.getFecha());
        pago.setMesCorrespondiente(request.getMesCorrespondiente());
        pago.setTipo(request.getTipo());
        pago.setEstado(request.getEstado());
        pago.setMetodoPago(request.getMetodoPago());
        pago.setReferenciaTransaccion(request.getReferenciaTransaccion());
        pago.setObservaciones(request.getObservaciones());
        pago.setFechaVencimiento(request.getFechaVencimiento());
        pago.setMora(request.getMora() != null ? request.getMora() : java.math.BigDecimal.ZERO);

        return pagoRepository.save(pago);
    }

    public List<Pago> obtenerHistorialPorContrato(Long contratoId) {
        return pagoRepository.findByContratoIdOrderByMesDesc(contratoId);
    }

    public List<Pago> obtenerPorArrendatario(Long arrendatarioId) {
        return pagoRepository.findByArrendatarioId(arrendatarioId);
    }

    public Optional<Pago> obtenerPorId(Long id) {
        return pagoRepository.findById(id);
    }

    public List<Pago> obtenerPagosPendientes() {
        return pagoRepository.findByEstado(Pago.EstadoPago.PENDIENTE);
    }

    public List<Pago> obtenerPagosVencidos() {
        return pagoRepository.findPagosVencidos(LocalDate.now());
    }

    public List<Pago> obtenerPagosPorVencer(int dias) {
        LocalDate fechaInicio = LocalDate.now();
        LocalDate fechaFin = fechaInicio.plusDays(dias);
        return pagoRepository.findPagosPorVencer(fechaInicio, fechaFin);
    }

    public List<Pago> obtenerPagosPorMes(int year, int month) {
        return pagoRepository.findPagosByYearAndMonth(year, month);
    }

    public List<Pago> obtenerPorTipo(Pago.TipoPago tipo) {
        return pagoRepository.findByTipo(tipo);
    }

    public Pago actualizarEstadoPago(Long id, Pago.EstadoPago nuevoEstado) {
        Optional<Pago> pagoOpt = pagoRepository.findById(id);
        if (pagoOpt.isPresent()) {
            Pago pago = pagoOpt.get();
            pago.setEstado(nuevoEstado);
            return pagoRepository.save(pago);
        }
        throw new RuntimeException("Pago no encontrado");
    }

    public Long contarPagosPendientesPorContrato(Long contratoId) {
        return pagoRepository.countPagosPendientesByContrato(contratoId);
    }

    private Boolean validarContratoExiste(Long contratoId) {
        try {
            WebClient webClient = webClientBuilder.build();
            return webClient.get()
                    .uri(contratosServiceUrl + "/api/contratos/" + contratoId)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .map(contrato -> true)
                    .onErrorReturn(false)
                    .block();
        } catch (Exception e) {
            return false;
        }
    }
}
