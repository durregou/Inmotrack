package com.arrendamiento.inmuebles.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "inmuebles")
public class Inmueble {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inm_id")
    private Long id;
    
    @NotNull(message = "El ID del propietario es obligatorio")
    @Column(name = "prop_id", nullable = false)
    private Long propietarioId;
    
    @NotBlank(message = "El tipo de inmueble es obligatorio")
    @Size(max = 50, message = "El tipo no puede exceder 50 caracteres")
    @Column(name = "inm_tipo", nullable = false, length = 50)
    private String tipo; // Casa, Apartamento, Local, Oficina, etc.
    
    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 200, message = "La dirección no puede exceder 200 caracteres")
    @Column(name = "inm_direccion", nullable = false, length = 200)
    private String direccion;
    
    @Size(max = 100, message = "La ciudad no puede exceder 100 caracteres")
    @Column(name = "inm_ciudad", length = 100)
    private String ciudad;
    
    @Size(max = 100, message = "El departamento no puede exceder 100 caracteres")
    @Column(name = "inm_departamento", length = 100)
    private String departamento;
    
    @Column(name = "inm_area")
    private Double area; // Área en metros cuadrados
    
    @Column(name = "inm_habitaciones")
    private Integer habitaciones;
    
    @Column(name = "inm_banos")
    private Integer banos;
    
    @Column(name = "inm_parqueaderos")
    private Integer parqueaderos;
    
    @NotNull(message = "El precio de arriendo es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Column(name = "inm_precio_arriendo", nullable = false, precision = 15, scale = 2)
    private BigDecimal precioArriendo;
    
    @Column(name = "inm_precio_administracion", precision = 15, scale = 2)
    private BigDecimal precioAdministracion;
    
    @Column(name = "inm_descripcion", length = 1000)
    private String descripcion;
    
    @Column(name = "inm_amoblado")
    private Boolean amoblado = false;
    
    @Column(name = "inm_disponible")
    private Boolean disponible = true;
    
    @Column(name = "inm_fecha_registro")
    private LocalDateTime fechaRegistro;
    
    @Column(name = "inm_activo")
    private Boolean activo = true;

    // Constructores
    public Inmueble() {
        this.fechaRegistro = LocalDateTime.now();
        this.amoblado = false;
        this.disponible = true;
        this.activo = true;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
