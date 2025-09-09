package com.arrendamiento.contratos.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "contratos")
public class Contrato {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cont_id")
    private Long id;
    
    @NotNull(message = "El ID del inmueble es obligatorio")
    @Column(name = "inm_id", nullable = false)
    private Long inmuebleId;
    
    @NotNull(message = "El ID del propietario es obligatorio")
    @Column(name = "prop_id", nullable = false)
    private Long propietarioId;
    
    @NotNull(message = "El ID del arrendatario es obligatorio")
    @Column(name = "arr_id", nullable = false)
    private Long arrendatarioId;
    
    @NotNull(message = "La fecha de inicio es obligatoria")
    @Column(name = "cont_fecha_inicio", nullable = false)
    private LocalDate fechaInicio;
    
    @NotNull(message = "La fecha de fin es obligatoria")
    @Column(name = "cont_fecha_fin", nullable = false)
    private LocalDate fechaFin;
    
    @NotNull(message = "El valor del arriendo es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El valor debe ser mayor a 0")
    @Column(name = "cont_valor_arriendo", nullable = false, precision = 15, scale = 2)
    private BigDecimal valorArriendo;
    
    @Column(name = "cont_valor_administracion", precision = 15, scale = 2)
    private BigDecimal valorAdministracion;
    
    @Column(name = "cont_deposito", precision = 15, scale = 2)
    private BigDecimal deposito;
    
    @Column(name = "cont_dia_pago")
    private Integer diaPago; // Día del mes para pagar (1-31)
    
    @Enumerated(EnumType.STRING)
    @Column(name = "cont_estado", length = 20)
    private EstadoContrato estado = EstadoContrato.ACTIVO;
    
    @Column(name = "cont_observaciones", length = 1000)
    private String observaciones;
    
    @Column(name = "cont_fecha_creacion")
    private LocalDateTime fechaCreacion;
    
    @Column(name = "cont_activo")
    private Boolean activo = true;

    public enum EstadoContrato {
        ACTIVO, FINALIZADO, CANCELADO, SUSPENDIDO
    }

    // Constructores
    public Contrato() {
        this.fechaCreacion = LocalDateTime.now();
        this.estado = EstadoContrato.ACTIVO;
        this.activo = true;
        this.diaPago = 5; // Por defecto el día 5 de cada mes
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public EstadoContrato getEstado() {
        return estado;
    }

    public void setEstado(EstadoContrato estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
