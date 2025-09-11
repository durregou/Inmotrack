package com.arrendamiento.pagos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
public class Pago {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pago_id")
    private Long id;
    
    @NotNull(message = "El ID del contrato es obligatorio")
    @Column(name = "cont_id", nullable = false)
    private Long contratoId;
    
    @NotNull(message = "El ID del arrendatario es obligatorio")
    @Column(name = "arr_id", nullable = false)
    private Long arrendatarioId;
    
    @NotNull(message = "El valor del pago es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El valor debe ser mayor a 0")
    @Column(name = "pago_valor", nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;
    
    @NotNull(message = "La fecha del pago es obligatoria")
    @Column(name = "pago_fecha", nullable = false)
    private LocalDate fecha;
    
    @NotNull(message = "El mes correspondiente es obligatorio")
    @Column(name = "pago_mes_correspondiente", nullable = false)
    private LocalDate mesCorrespondiente; // Mes al que corresponde el pago
    
    @Enumerated(EnumType.STRING)
    @Column(name = "pago_tipo", length = 20)
    private TipoPago tipo = TipoPago.ARRIENDO;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "pago_estado", length = 20)
    private EstadoPago estado = EstadoPago.PAGADO;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "pago_metodo", length = 30)
    private MetodoPago metodoPago;
    
    @Column(name = "pago_referencia_transaccion", length = 100)
    private String referenciaTransaccion;
    
    @Column(name = "pago_observaciones", length = 500)
    private String observaciones;
    
    @Column(name = "pago_fecha_vencimiento")
    private LocalDate fechaVencimiento;
    
    @Column(name = "pago_mora", precision = 15, scale = 2)
    private BigDecimal mora = BigDecimal.ZERO;
    
    @Column(name = "pago_fecha_registro")
    private LocalDateTime fechaRegistro;

    public enum TipoPago {
        ARRIENDO, ADMINISTRACION, DEPOSITO, MORA, SERVICIOS_PUBLICOS, OTROS
    }

    public enum EstadoPago {
        PENDIENTE, PAGADO, VENCIDO, PARCIAL
    }

    public enum MetodoPago {
        EFECTIVO, TRANSFERENCIA_BANCARIA, CHEQUE, TARJETA_CREDITO, TARJETA_DEBITO, PSE, NEQUI, DAVIPLATA
    }

    // Constructores
    public Pago() {
        this.fechaRegistro = LocalDateTime.now();
        this.tipo = TipoPago.ARRIENDO;
        this.estado = EstadoPago.PAGADO;
        this.mora = BigDecimal.ZERO;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public TipoPago getTipo() {
        return tipo;
    }

    public void setTipo(TipoPago tipo) {
        this.tipo = tipo;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    public void setEstado(EstadoPago estado) {
        this.estado = estado;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
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

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
