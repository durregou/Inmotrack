package com.arrendamiento.reportes.controller;

import com.arrendamiento.reportes.dto.ReporteFlujo;
import com.arrendamiento.reportes.dto.ReporteOcupacion;
import com.arrendamiento.reportes.dto.ReporteRentabilidad;
import com.arrendamiento.reportes.service.ExportService;
import com.arrendamiento.reportes.service.ReportesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@CrossOrigin(origins = "*")
public class ReportesController {
    
    @Autowired
    private ReportesService reportesService;
    
    @Autowired
    private ExportService exportService;
    
    @GetMapping("/rentabilidad")
    public ResponseEntity<?> obtenerReporteRentabilidad(
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {
        
        try {
            if (fechaInicio == null) {
                fechaInicio = LocalDate.now().minusMonths(1).toString();
            }
            if (fechaFin == null) {
                fechaFin = LocalDate.now().toString();
            }
            
            ReporteRentabilidad reporte = reportesService.generarReporteRentabilidad(fechaInicio, fechaFin);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    @GetMapping("/ocupacion")
    public ResponseEntity<?> obtenerReporteOcupacion() {
        try {
            ReporteOcupacion reporte = reportesService.generarReporteOcupacion();
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    @GetMapping("/flujo-financiero")
    public ResponseEntity<?> obtenerReporteFlujo(
            @RequestParam(required = false) String mes,
            @RequestParam(required = false) String anio) {
        
        try {
            if (mes == null) {
                mes = String.valueOf(LocalDate.now().getMonthValue());
            }
            if (anio == null) {
                anio = String.valueOf(LocalDate.now().getYear());
            }
            
            ReporteFlujo reporte = reportesService.generarReporteFlujoFinanciero(mes, anio);
            return ResponseEntity.ok(reporte);
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
    
    @GetMapping("/rentabilidad/excel")
    public ResponseEntity<byte[]> exportarRentabilidadExcel(
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin) {
        
        try {
            if (fechaInicio == null) fechaInicio = LocalDate.now().minusMonths(1).toString();
            if (fechaFin == null) fechaFin = LocalDate.now().toString();
            
            ReporteRentabilidad reporte = reportesService.generarReporteRentabilidad(fechaInicio, fechaFin);
            byte[] excelBytes = exportService.exportarRentabilidadExcel(reporte);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "reporte_rentabilidad.xlsx");
            
            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/ocupacion/excel")
    public ResponseEntity<byte[]> exportarOcupacionExcel() {
        try {
            ReporteOcupacion reporte = reportesService.generarReporteOcupacion();
            byte[] excelBytes = exportService.exportarOcupacionExcel(reporte);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "reporte_ocupacion.xlsx");
            
            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/flujo-financiero/excel")
    public ResponseEntity<byte[]> exportarFlujoExcel(
            @RequestParam(required = false) String mes,
            @RequestParam(required = false) String anio) {
        
        try {
            if (mes == null) mes = String.valueOf(LocalDate.now().getMonthValue());
            if (anio == null) anio = String.valueOf(LocalDate.now().getYear());
            
            ReporteFlujo reporte = reportesService.generarReporteFlujoFinanciero(mes, anio);
            byte[] excelBytes = exportService.exportarFlujoExcel(reporte);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", "reporte_flujo_financiero.xlsx");
            
            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

