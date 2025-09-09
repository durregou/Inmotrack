package com.arrendamiento.administracion.dto;

public class LoginResponse {
    private String token;
    private String tipo = "Bearer";
    private Long id;
    private String nombre;
    private String correo;

    // Constructores
    public LoginResponse() {}
    
    public LoginResponse(String token, Long id, String nombre, String correo) {
        this.token = token;
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
    }

    // Getters y Setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

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
}
