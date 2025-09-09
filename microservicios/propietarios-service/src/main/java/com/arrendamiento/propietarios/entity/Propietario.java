package com.arrendamiento.propietarios.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "propietarios")
public class Propietario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prop_id")
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Column(name = "prop_nombre", nullable = false, length = 100)
    private String nombre;
    
    @Size(max = 100, message = "El apellido no puede exceder 100 caracteres")
    @Column(name = "prop_apellido", length = 100)
    private String apellido;
    
    @Email(message = "El formato del correo no es válido")
    @NotBlank(message = "El correo es obligatorio")
    @Column(name = "prop_correo", nullable = false, unique = true, length = 150)
    private String correo;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Column(name = "prop_contrasena", nullable = false)
    private String contrasena;
    
    @Column(name = "prop_telefono", length = 15)
    private String telefono;
    
    @Column(name = "prop_direccion", length = 200)
    private String direccion;
    
    @Column(name = "prop_cedula", length = 20)
    private String cedula;
    
    @Column(name = "prop_fecha_registro")
    private LocalDateTime fechaRegistro;
    
    @Column(name = "prop_activo")
    private Boolean activo = true;

    // Constructores
    public Propietario() {
        this.fechaRegistro = LocalDateTime.now();
        this.activo = true;
    }
    
    public Propietario(String nombre, String apellido, String correo, String contrasena, String telefono, String direccion, String cedula) {
        this();
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.direccion = direccion;
        this.cedula = cedula;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
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
