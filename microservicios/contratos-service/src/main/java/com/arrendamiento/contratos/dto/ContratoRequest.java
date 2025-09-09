package com.arrendamiento.contratos.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public class ContratoRequest {
    
    @NotNull(message = "El ID del inmueble es obligatorio")
    private Long inmuebleId;
    
    @NotNull(message = "El ID del propietario es obligatorio")
    private Long propietarioId;
    
    @NotNull(message = "El ID del arrendatario es obligatorio")
    private Long arrendatarioId;
    
    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;
    
    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDate fechaFin;
    
    @NotNull(message = "El valor del arriendo es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El valor debe ser mayor a 0")
    private BigDecimal valorArriendo;
    
    private BigDecimal valorAdministracion;
    private BigDecimal deposito;
    
    @Min(value = 1, message = "El día de pago debe ser entre 1 y 31")
    @Max(value = 31, message = "El día de pago debe ser entre 1 y 31")
    private Integer diaPago = 5;
    
    @Size(max = 1000, message = "Las observaciones no pueden exceder 1000 caracteres")
    private String observaciones;

    // Constructores
    public ContratoRequest() {}

    // Getters y Setters
    public Long getInmuebleId() {
        return inmuebleId;
    }

    public void setInmuebleId(Long inmuebleId) {
        this.inmuebleId = inmuebleId;
    }

    public Long getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(Long propietarioId) {
        this.propietarioId = propietarioId;
    }

    public Long getArrendatarioId() {
        return arrendatarioId;
    }

    public void setArrendatarioId(Long arrendatarioId) {
        this.arrendatarioId = arrendatarioId;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public BigDecimal getValorArriendo() {
        return valorArriendo;
    }

    public void setValorArriendo(BigDecimal valorArriendo) {
        this.valorArriendo = valorArriendo;
    }

    public BigDecimal getValorAdministracion() {
        return valorAdministracion;
    }

    public void setValorAdministracion(BigDecimal valorAdministracion) {
        this.valorAdministracion = valorAdministracion;
    }

    public BigDecimal getDeposito() {
        return deposito;
    }

    public void setDeposito(BigDecimal deposito) {
        this.deposito = deposito;
    }

    public Integer getDiaPago() {
        return diaPago;
    }

    public void setDiaPago(Integer diaPago) {
        this.diaPago = diaPago;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}
