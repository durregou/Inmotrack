package com.arrendamiento.reportes.service;

import com.arrendamiento.reportes.dto.ReporteFlujo;
import com.arrendamiento.reportes.dto.ReporteOcupacion;
import com.arrendamiento.reportes.dto.ReporteRentabilidad;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ExportService {
    
    public byte[] exportarRentabilidadExcel(ReporteRentabilidad reporte) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Reporte de Rentabilidad");
            
            // Crear encabezados
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Métrica", "Valor"};
            
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
                CellStyle headerStyle = workbook.createCellStyle();
                Font font = workbook.createFont();
                font.setBold(true);
                headerStyle.setFont(font);
                cell.setCellStyle(headerStyle);
            }
            
            // Agregar datos
            int rowNum = 1;
            crearFila(sheet, rowNum++, "Ingresos Totales", reporte.getIngresosTotales());
            crearFila(sheet, rowNum++, "Gastos Totales", reporte.getGastosTotales());
            crearFila(sheet, rowNum++, "Rentabilidad Neta", reporte.getRentabilidadNeta());
            crearFila(sheet, rowNum++, "Total Contratos", reporte.getTotalContratos().doubleValue());
            crearFila(sheet, rowNum++, "Contratos Activos", reporte.getContratosActivos().doubleValue());
            crearFila(sheet, rowNum++, "Promedio Renta", reporte.getPromedioRenta());
            crearFila(sheet, rowNum++, "Periodo", reporte.getPeriodo());
            
            // Ajustar ancho de columnas
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }
    
    public byte[] exportarOcupacionExcel(ReporteOcupacion reporte) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Reporte de Ocupación");
            
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Métrica", "Valor"};
            
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }
            
            int rowNum = 1;
            crearFila(sheet, rowNum++, "Total Inmuebles", reporte.getTotalInmuebles().doubleValue());
            crearFila(sheet, rowNum++, "Inmuebles Arrendados", reporte.getInmueblesArrendados().doubleValue());
            crearFila(sheet, rowNum++, "Inmuebles Disponibles", reporte.getInmueblesDisponibles().doubleValue());
            crearFila(sheet, rowNum++, "Inmuebles en Mantenimiento", reporte.getInmueblesMantenimiento().doubleValue());
            crearFila(sheet, rowNum++, "Tasa de Ocupación (%)", reporte.getTasaOcupacion());
            crearFila(sheet, rowNum++, "Periodo", reporte.getPeriodo());
            
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }
    
    public byte[] exportarFlujoExcel(ReporteFlujo reporte) throws IOException {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Reporte de Flujo Financiero");
            
            Row headerRow = sheet.createRow(0);
            String[] columns = {"Métrica", "Valor"};
            
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }
            
            int rowNum = 1;
            crearFila(sheet, rowNum++, "Ingresos Mensuales", reporte.getIngresosMensuales());
            crearFila(sheet, rowNum++, "Egresos Mensuales", reporte.getEgresosMensuales());
            crearFila(sheet, rowNum++, "Flujo Neto", reporte.getFlujoNeto());
            crearFila(sheet, rowNum++, "Pagos Pendientes", reporte.getPagosPendientes().doubleValue());
            crearFila(sheet, rowNum++, "Monto Pendiente", reporte.getMontoPendiente());
            crearFila(sheet, rowNum++, "Pagos Realizados", reporte.getPagosRealizados().doubleValue());
            crearFila(sheet, rowNum++, "Monto Recaudado", reporte.getMontoRecaudado());
            crearFila(sheet, rowNum++, "Periodo", reporte.getPeriodo());
            
            for (int i = 0; i < columns.length; i++) {
                sheet.autoSizeColumn(i);
            }
            
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        }
    }
    
    private void crearFila(Sheet sheet, int rowNum, String metrica, Object valor) {
        Row row = sheet.createRow(rowNum);
        row.createCell(0).setCellValue(metrica);
        
        if (valor instanceof Number) {
            row.createCell(1).setCellValue(((Number) valor).doubleValue());
        } else {
            row.createCell(1).setCellValue(valor.toString());
        }
    }
}

