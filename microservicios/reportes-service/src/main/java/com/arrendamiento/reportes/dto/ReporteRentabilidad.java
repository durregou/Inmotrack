package com.arrendamiento.reportes.dto;

public class ReporteRentabilidad {
    private Double ingresosTotales;
    private Double gastosTotales;
    private Double rentabilidadNeta;
    private Integer totalContratos;
    private Integer contratosActivos;
    private Double promedioRenta;
    private String periodo;
    
    public ReporteRentabilidad() {
    }
    
    public ReporteRentabilidad(Double ingresosTotales, Double gastosTotales, Double rentabilidadNeta, 
                               Integer totalContratos, Integer contratosActivos, Double promedioRenta, String periodo) {
        this.ingresosTotales = ingresosTotales;
        this.gastosTotales = gastosTotales;
        this.rentabilidadNeta = rentabilidadNeta;
        this.totalContratos = totalContratos;
        this.contratosActivos = contratosActivos;
        this.promedioRenta = promedioRenta;
        this.periodo = periodo;
    }
    
    // Getters y Setters
    public Double getIngresosTotales() {
        return ingresosTotales;
    }
    
    public void setIngresosTotales(Double ingresosTotales) {
        this.ingresosTotales = ingresosTotales;
    }
    
    public Double getGastosTotales() {
        return gastosTotales;
    }
    
    public void setGastosTotales(Double gastosTotales) {
        this.gastosTotales = gastosTotales;
    }
    
    public Double getRentabilidadNeta() {
        return rentabilidadNeta;
    }
    
    public void setRentabilidadNeta(Double rentabilidadNeta) {
        this.rentabilidadNeta = rentabilidadNeta;
    }
    
    public Integer getTotalContratos() {
        return totalContratos;
    }
    
    public void setTotalContratos(Integer totalContratos) {
        this.totalContratos = totalContratos;
    }
    
    public Integer getContratosActivos() {
        return contratosActivos;
    }
    
    public void setContratosActivos(Integer contratosActivos) {
        this.contratosActivos = contratosActivos;
    }
    
    public Double getPromedioRenta() {
        return promedioRenta;
    }
    
    public void setPromedioRenta(Double promedioRenta) {
        this.promedioRenta = promedioRenta;
    }
    
    public String getPeriodo() {
        return periodo;
    }
    
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}

