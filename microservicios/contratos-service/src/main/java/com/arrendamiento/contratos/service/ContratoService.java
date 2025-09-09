package com.arrendamiento.contratos.service;

import com.arrendamiento.contratos.dto.ContratoRequest;
import com.arrendamiento.contratos.entity.Contrato;
import com.arrendamiento.contratos.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${propietarios.service.url:http://localhost:8082}")
    private String propietariosServiceUrl;

    @Value("${inmuebles.service.url:http://localhost:8083}")
    private String inmueblesServiceUrl;

    public Contrato crearContrato(ContratoRequest request) {
        // Validar que el inmueble existe y está disponible
        Boolean inmuebleDisponible = validarInmuebleDisponible(request.getInmuebleId());
        if (!inmuebleDisponible) {
            throw new RuntimeException("El inmueble no está disponible para arrendamiento");
        }

        // Validar que el propietario existe
        Boolean propietarioExiste = validarPropietarioExiste(request.getPropietarioId());
        if (!propietarioExiste) {
            throw new RuntimeException("El propietario especificado no existe");
        }

        // Validar que no existe un contrato activo para ese inmueble
        boolean existeContratoActivo = contratoRepository.existsByInmuebleIdAndEstado(
            request.getInmuebleId(), Contrato.EstadoContrato.ACTIVO);
        if (existeContratoActivo) {
            throw new RuntimeException("Ya existe un contrato activo para este inmueble");
        }

        // Validar fechas
        if (request.getFechaFin().isBefore(request.getFechaInicio())) {
            throw new RuntimeException("La fecha de fin no puede ser anterior a la fecha de inicio");
        }

        // Crear el contrato
        Contrato contrato = new Contrato();
        contrato.setInmuebleId(request.getInmuebleId());
        contrato.setPropietarioId(request.getPropietarioId());
        contrato.setArrendatarioId(request.getArrendatarioId());
        contrato.setFechaInicio(request.getFechaInicio());
        contrato.setFechaFin(request.getFechaFin());
        contrato.setValorArriendo(request.getValorArriendo());
        contrato.setValorAdministracion(request.getValorAdministracion());
        contrato.setDeposito(request.getDeposito());
        contrato.setDiaPago(request.getDiaPago());
        contrato.setObservaciones(request.getObservaciones());

        Contrato contratoGuardado = contratoRepository.save(contrato);

        // Marcar el inmueble como no disponible
        actualizarDisponibilidadInmueble(request.getInmuebleId(), false);

        return contratoGuardado;
    }

    public Optional<Contrato> obtenerPorId(Long id) {
        return contratoRepository.findById(id);
    }

    public List<Contrato> obtenerPorPropietario(Long propietarioId) {
        return contratoRepository.findByPropietarioId(propietarioId);
    }

    public List<Contrato> obtenerPorArrendatario(Long arrendatarioId) {
        return contratoRepository.findByArrendatarioId(arrendatarioId);
    }

    public List<Contrato> obtenerPorInmueble(Long inmuebleId) {
        return contratoRepository.findByInmuebleId(inmuebleId);
    }

    public List<Contrato> obtenerActivos() {
        return contratoRepository.findByEstado(Contrato.EstadoContrato.ACTIVO);
    }

    public Contrato finalizarContrato(Long id) {
        Optional<Contrato> contratoOpt = contratoRepository.findById(id);
        if (contratoOpt.isPresent()) {
            Contrato contrato = contratoOpt.get();
            contrato.setEstado(Contrato.EstadoContrato.FINALIZADO);
            
            // Marcar el inmueble como disponible nuevamente
            actualizarDisponibilidadInmueble(contrato.getInmuebleId(), true);
            
            return contratoRepository.save(contrato);
        }
        throw new RuntimeException("Contrato no encontrado");
    }

    private Boolean validarInmuebleDisponible(Long inmuebleId) {
        try {
            WebClient webClient = webClientBuilder.build();
            return webClient.get()
                    .uri(inmueblesServiceUrl + "/api/inmuebles/" + inmuebleId)
                    .retrieve()
                    .bodyToMono(Object.class) // Por simplicidad, usamos Object
                    .map(inmueble -> true) // Si existe, asumimos que está disponible
                    .onErrorReturn(false)
                    .block();
        } catch (Exception e) {
            return false;
        }
    }

    private Boolean validarPropietarioExiste(Long propietarioId) {
        try {
            WebClient webClient = webClientBuilder.build();
            return webClient.get()
                    .uri(propietariosServiceUrl + "/api/propietarios/" + propietarioId)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .map(propietario -> true)
                    .onErrorReturn(false)
                    .block();
        } catch (Exception e) {
            return false;
        }
    }

    private void actualizarDisponibilidadInmueble(Long inmuebleId, Boolean disponible) {
        try {
            WebClient webClient = webClientBuilder.build();
            webClient.put()
                    .uri(inmueblesServiceUrl + "/api/inmuebles/" + inmuebleId + "/disponibilidad?disponible=" + disponible)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (Exception e) {
            System.err.println("Error actualizando disponibilidad del inmueble: " + e.getMessage());
        }
    }
}
