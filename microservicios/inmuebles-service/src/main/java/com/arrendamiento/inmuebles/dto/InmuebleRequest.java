package com.arrendamiento.inmuebles.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class InmuebleRequest {
    
    @NotNull(message = "El ID del propietario es obligatorio")
    private Long propietarioId;
    
    @NotBlank(message = "El tipo de inmueble es obligatorio")
    @Size(max = 50, message = "El tipo no puede exceder 50 caracteres")
    private String tipo;
    
    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 200, message = "La dirección no puede exceder 200 caracteres")
    private String direccion;
    
    @Size(max = 100, message = "La ciudad no puede exceder 100 caracteres")
    private String ciudad;
    
    @Size(max = 100, message = "El departamento no puede exceder 100 caracteres")
    private String departamento;
    
    private Double area;
    private Integer habitaciones;
    private Integer banos;
    private Integer parqueaderos;
    
    @NotNull(message = "El precio de arriendo es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private BigDecimal precioArriendo;
    
    private BigDecimal precioAdministracion;
    
    @Size(max = 1000, message = "La descripción no puede exceder 1000 caracteres")
    private String descripcion;
    
    private Boolean amoblado = false;
    private Boolean disponible = true;

    // Constructores
    public InmuebleRequest() {}

    // Getters y Setters
    public Long getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(Long propietarioId) {
        this.propietarioId = propietarioId;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public Integer getHabitaciones() {
        return habitaciones;
    }

    public void setHabitaciones(Integer habitaciones) {
        this.habitaciones = habitaciones;
    }

    public Integer getBanos() {
        return banos;
    }

    public void setBanos(Integer banos) {
        this.banos = banos;
    }

    public Integer getParqueaderos() {
        return parqueaderos;
    }

    public void setParqueaderos(Integer parqueaderos) {
        this.parqueaderos = parqueaderos;
    }

    public BigDecimal getPrecioArriendo() {
        return precioArriendo;
    }

    public void setPrecioArriendo(BigDecimal precioArriendo) {
        this.precioArriendo = precioArriendo;
    }

    public BigDecimal getPrecioAdministracion() {
        return precioAdministracion;
    }

    public void setPrecioAdministracion(BigDecimal precioAdministracion) {
        this.precioAdministracion = precioAdministracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getAmoblado() {
        return amoblado;
    }

    public void setAmoblado(Boolean amoblado) {
        this.amoblado = amoblado;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }
}
