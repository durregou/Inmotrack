package com.arrendamiento.pagos.dto;

import com.arrendamiento.pagos.entity.Pago;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

public class PagoRequest {
    
    @NotNull(message = "El ID del contrato es obligatorio")
    private Long contratoId;
    
    @NotNull(message = "El ID del arrendatario es obligatorio")
    private Long arrendatarioId;
    
    @NotNull(message = "El valor del pago es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El valor debe ser mayor a 0")
    private BigDecimal valor;
    
    @NotNull(message = "La fecha del pago es obligatoria")
    private LocalDate fecha;
    
    @NotNull(message = "El mes correspondiente es obligatorio")
    private LocalDate mesCorrespondiente;
    
    private Pago.TipoPago tipo = Pago.TipoPago.ARRIENDO;
    private Pago.EstadoPago estado = Pago.EstadoPago.PAGADO;
    private Pago.MetodoPago metodoPago;
    
    @Size(max = 100, message = "La referencia no puede exceder 100 caracteres")
    private String referenciaTransaccion;
    
    @Size(max = 500, message = "Las observaciones no pueden exceder 500 caracteres")
    private String observaciones;
    
    private LocalDate fechaVencimiento;
    private BigDecimal mora = BigDecimal.ZERO;

    // Constructores
    public PagoRequest() {}

    // Getters y Setters
    public Long getContratoId() {
        return contratoId;
    }

    public void setContratoId(Long contratoId) {
        this.contratoId = contratoId;
    }

    public Long getArrendatarioId() {
        return arrendatarioId;
    }

    public void setArrendatarioId(Long arrendatarioId) {
        this.arrendatarioId = arrendatarioId;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalDate getMesCorrespondiente() {
        return mesCorrespondiente;
    }

    public void setMesCorrespondiente(LocalDate mesCorrespondiente) {
        this.mesCorrespondiente = mesCorrespondiente;
    }

    public Pago.TipoPago getTipo() {
        return tipo;
    }

    public void setTipo(Pago.TipoPago tipo) {
        this.tipo = tipo;
    }

    public Pago.EstadoPago getEstado() {
        return estado;
    }

    public void setEstado(Pago.EstadoPago estado) {
        this.estado = estado;
    }

    public Pago.MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(Pago.MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    public String getReferenciaTransaccion() {
        return referenciaTransaccion;
    }

    public void setReferenciaTransaccion(String referenciaTransaccion) {
        this.referenciaTransaccion = referenciaTransaccion;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public BigDecimal getMora() {
        return mora;
    }

    public void setMora(BigDecimal mora) {
        this.mora = mora;
    }
}
