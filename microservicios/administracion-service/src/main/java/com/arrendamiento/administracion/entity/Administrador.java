package com.arrendamiento.administracion.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "administradores")
public class Administrador {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adm_id")
    private Long id;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Column(name = "adm_nombre", nullable = false, length = 100)
    private String nombre;
    
    @Email(message = "El formato del correo no es válido")
    @NotBlank(message = "El correo es obligatorio")
    @Column(name = "adm_correo", nullable = false, unique = true, length = 150)
    private String correo;
    
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    @Column(name = "adm_contrasena", nullable = false)
    private String contrasena;
    
    @Column(name = "adm_telefono", length = 15)
    private String telefono;
    
    @Column(name = "adm_activo")
    private Boolean activo = true;

    // Constructores
    public Administrador() {}
    
    public Administrador(String nombre, String correo, String contrasena, String telefono) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
        this.telefono = telefono;
        this.activo = true;
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

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
}
