package com.arrendamiento.usuarios.dto;

import jakarta.validation.constraints.NotBlank;

public class RestablecerPasswordRequest {
    
    @NotBlank(message = "El token es obligatorio")
    private String token;
    
    @NotBlank(message = "La nueva contraseña es obligatoria")
    private String nuevaContrasena;
    
    // Getters y Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getNuevaContrasena() {
        return nuevaContrasena;
    }
    
    public void setNuevaContrasena(String nuevaContrasena) {
        this.nuevaContrasena = nuevaContrasena;
    }
}

