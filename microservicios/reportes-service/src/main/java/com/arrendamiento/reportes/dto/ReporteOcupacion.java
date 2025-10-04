package com.arrendamiento.reportes.dto;

public class ReporteOcupacion {
    private Integer totalInmuebles;
    private Integer inmueblesArrendados;
    private Integer inmueblesDisponibles;
    private Integer inmueblesMantenimiento;
    private Double tasaOcupacion;
    private String periodo;
    
    public ReporteOcupacion() {
    }
    
    public ReporteOcupacion(Integer totalInmuebles, Integer inmueblesArrendados, Integer inmueblesDisponibles, 
                           Integer inmueblesMantenimiento, Double tasaOcupacion, String periodo) {
        this.totalInmuebles = totalInmuebles;
        this.inmueblesArrendados = inmueblesArrendados;
        this.inmueblesDisponibles = inmueblesDisponibles;
        this.inmueblesMantenimiento = inmueblesMantenimiento;
        this.tasaOcupacion = tasaOcupacion;
        this.periodo = periodo;
    }
    
    // Getters y Setters
    public Integer getTotalInmuebles() {
        return totalInmuebles;
    }
    
    public void setTotalInmuebles(Integer totalInmuebles) {
        this.totalInmuebles = totalInmuebles;
    }
    
    public Integer getInmueblesArrendados() {
        return inmueblesArrendados;
    }
    
    public void setInmueblesArrendados(Integer inmueblesArrendados) {
        this.inmueblesArrendados = inmueblesArrendados;
    }
    
    public Integer getInmueblesDisponibles() {
        return inmueblesDisponibles;
    }
    
    public void setInmueblesDisponibles(Integer inmueblesDisponibles) {
        this.inmueblesDisponibles = inmueblesDisponibles;
    }
    
    public Integer getInmueblesMantenimiento() {
        return inmueblesMantenimiento;
    }
    
    public void setInmueblesMantenimiento(Integer inmueblesMantenimiento) {
        this.inmueblesMantenimiento = inmueblesMantenimiento;
    }
    
    public Double getTasaOcupacion() {
        return tasaOcupacion;
    }
    
    public void setTasaOcupacion(Double tasaOcupacion) {
        this.tasaOcupacion = tasaOcupacion;
    }
    
    public String getPeriodo() {
        return periodo;
    }
    
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}

