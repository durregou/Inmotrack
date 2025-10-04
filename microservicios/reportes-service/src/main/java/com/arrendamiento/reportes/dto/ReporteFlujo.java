package com.arrendamiento.reportes.dto;

public class ReporteFlujo {
    private Double ingresosMensuales;
    private Double egresosMensuales;
    private Double flujoNeto;
    private Integer pagosPendientes;
    private Double montoPendiente;
    private Integer pagosRealizados;
    private Double montoRecaudado;
    private String periodo;
    
    public ReporteFlujo() {
    }
    
    public ReporteFlujo(Double ingresosMensuales, Double egresosMensuales, Double flujoNeto, 
                       Integer pagosPendientes, Double montoPendiente, Integer pagosRealizados, 
                       Double montoRecaudado, String periodo) {
        this.ingresosMensuales = ingresosMensuales;
        this.egresosMensuales = egresosMensuales;
        this.flujoNeto = flujoNeto;
        this.pagosPendientes = pagosPendientes;
        this.montoPendiente = montoPendiente;
        this.pagosRealizados = pagosRealizados;
        this.montoRecaudado = montoRecaudado;
        this.periodo = periodo;
    }
    
    // Getters y Setters
    public Double getIngresosMensuales() {
        return ingresosMensuales;
    }
    
    public void setIngresosMensuales(Double ingresosMensuales) {
        this.ingresosMensuales = ingresosMensuales;
    }
    
    public Double getEgresosMensuales() {
        return egresosMensuales;
    }
    
    public void setEgresosMensuales(Double egresosMensuales) {
        this.egresosMensuales = egresosMensuales;
    }
    
    public Double getFlujoNeto() {
        return flujoNeto;
    }
    
    public void setFlujoNeto(Double flujoNeto) {
        this.flujoNeto = flujoNeto;
    }
    
    public Integer getPagosPendientes() {
        return pagosPendientes;
    }
    
    public void setPagosPendientes(Integer pagosPendientes) {
        this.pagosPendientes = pagosPendientes;
    }
    
    public Double getMontoPendiente() {
        return montoPendiente;
    }
    
    public void setMontoPendiente(Double montoPendiente) {
        this.montoPendiente = montoPendiente;
    }
    
    public Integer getPagosRealizados() {
        return pagosRealizados;
    }
    
    public void setPagosRealizados(Integer pagosRealizados) {
        this.pagosRealizados = pagosRealizados;
    }
    
    public Double getMontoRecaudado() {
        return montoRecaudado;
    }
    
    public void setMontoRecaudado(Double montoRecaudado) {
        this.montoRecaudado = montoRecaudado;
    }
    
    public String getPeriodo() {
        return periodo;
    }
    
    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }
}

