package com.arrendamiento.administracion.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    
    @Email(message = "El formato del correo no es válido")
    @NotBlank(message = "El correo es obligatorio")
    private String correo;
    
    @NotBlank(message = "La contraseña es obligatoria")
    private String contrasena;

    // Constructores
    public LoginRequest() {}
    
    public LoginRequest(String correo, String contrasena) {
        this.correo = correo;
        this.contrasena = contrasena;
    }

    // Getters y Setters
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
}
