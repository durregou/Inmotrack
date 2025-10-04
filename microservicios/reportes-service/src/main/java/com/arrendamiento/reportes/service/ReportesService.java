package com.arrendamiento.reportes.service;

import com.arrendamiento.reportes.dto.ReporteRentabilidad;
import com.arrendamiento.reportes.dto.ReporteOcupacion;
import com.arrendamiento.reportes.dto.ReporteFlujo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class ReportesService {
    
    @Value("${contratos.service.url:http://localhost:8084}")
    private String contratosServiceUrl;
    
    @Value("${pagos.service.url:http://localhost:8085}")
    private String pagosServiceUrl;
    
    @Value("${inmuebles.service.url:http://localhost:8083}")
    private String inmueblesServiceUrl;
    
    @Value("${mantenimiento.service.url:http://localhost:8088}")
    private String mantenimientoServiceUrl;
    
    private final WebClient webClient;
    
    @Autowired
    public ReportesService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }
    
    public ReporteRentabilidad generarReporteRentabilidad(String fechaInicio, String fechaFin) {
        try {
            // Obtener datos de pagos
            List pagos = webClient.get()
                .uri(pagosServiceUrl + "/api/pagos")
                .retrieve()
                .bodyToFlux(Map.class)
                .collectList()
                .block();
            
            // Obtener datos de contratos
            List contratos = webClient.get()
                .uri(contratosServiceUrl + "/api/contratos")
                .retrieve()
                .bodyToFlux(Map.class)
                .collectList()
                .block();
            
            // Calcular métricas
            Double ingresosTotales = pagos != null ? pagos.size() * 1000.0 : 0.0; // Simplificado
            Double gastosTotales = 500.0; // Simplificado
            Double rentabilidadNeta = ingresosTotales - gastosTotales;
            Integer totalContratos = contratos != null ? contratos.size() : 0;
            Integer contratosActivos = totalContratos; // Simplificado
            Double promedioRenta = totalContratos > 0 ? ingresosTotales / totalContratos : 0.0;
            String periodo = fechaInicio + " a " + fechaFin;
            
            return new ReporteRentabilidad(ingresosTotales, gastosTotales, rentabilidadNeta, 
                                          totalContratos, contratosActivos, promedioRenta, periodo);
        } catch (Exception e) {
            System.err.println("Error al generar reporte de rentabilidad: " + e.getMessage());
            return new ReporteRentabilidad(0.0, 0.0, 0.0, 0, 0, 0.0, "Error");
        }
    }
    
    public ReporteOcupacion generarReporteOcupacion() {
        try {
            // Obtener datos de inmuebles
            List inmuebles = webClient.get()
                .uri(inmueblesServiceUrl + "/api/inmuebles")
                .retrieve()
                .bodyToFlux(Map.class)
                .collectList()
                .block();
            
            Integer totalInmuebles = inmuebles != null ? inmuebles.size() : 0;
            Integer inmueblesArrendados = (int) (totalInmuebles * 0.7); // Simplificado
            Integer inmueblesDisponibles = (int) (totalInmuebles * 0.2);
            Integer inmueblesMantenimiento = totalInmuebles - inmueblesArrendados - inmueblesDisponibles;
            Double tasaOcupacion = totalInmuebles > 0 ? (inmueblesArrendados * 100.0 / totalInmuebles) : 0.0;
            String periodo = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
            
            return new ReporteOcupacion(totalInmuebles, inmueblesArrendados, inmueblesDisponibles, 
                                       inmueblesMantenimiento, tasaOcupacion, periodo);
        } catch (Exception e) {
            System.err.println("Error al generar reporte de ocupación: " + e.getMessage());
            return new ReporteOcupacion(0, 0, 0, 0, 0.0, "Error");
        }
    }
    
    public ReporteFlujo generarReporteFlujoFinanciero(String mes, String anio) {
        try {
            // Obtener datos de pagos
            List pagos = webClient.get()
                .uri(pagosServiceUrl + "/api/pagos")
                .retrieve()
                .bodyToFlux(Map.class)
                .collectList()
                .block();
            
            Double ingresosMensuales = pagos != null ? pagos.size() * 1000.0 : 0.0; // Simplificado
            Double egresosMensuales = 300.0; // Simplificado
            Double flujoNeto = ingresosMensuales - egresosMensuales;
            Integer pagosPendientes = 5; // Simplificado
            Double montoPendiente = 5000.0;
            Integer pagosRealizados = pagos != null ? pagos.size() : 0;
            Double montoRecaudado = ingresosMensuales;
            String periodo = mes + "/" + anio;
            
            return new ReporteFlujo(ingresosMensuales, egresosMensuales, flujoNeto, 
                                   pagosPendientes, montoPendiente, pagosRealizados, 
                                   montoRecaudado, periodo);
        } catch (Exception e) {
            System.err.println("Error al generar reporte de flujo financiero: " + e.getMessage());
            return new ReporteFlujo(0.0, 0.0, 0.0, 0, 0.0, 0, 0.0, "Error");
        }
    }
}

