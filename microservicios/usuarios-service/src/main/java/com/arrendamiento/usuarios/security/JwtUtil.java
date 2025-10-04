package com.arrendamiento.usuarios.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    
    private static final String SECRET_KEY = "arrendamiento_secret_key_very_secure_2024_spring_boot_microservices";
    private static final long EXPIRATION_TIME = 86400000; // 24 horas
    
    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
    
    public String generateToken(String correo, String tipoUsuario, Long userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("tipoUsuario", tipoUsuario);
        claims.put("userId", userId);
        
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(correo)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }
    
    public String extractCorreo(String token) {
        return extractClaims(token).getSubject();
    }
    
    public String extractTipoUsuario(String token) {
        return (String) extractClaims(token).get("tipoUsuario");
    }
    
    public Long extractUserId(String token) {
        return ((Number) extractClaims(token).get("userId")).longValue();
    }
    
    public boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
    
    public boolean validateToken(String token, String correo) {
        return (correo.equals(extractCorreo(token)) && !isTokenExpired(token));
    }
    
    private Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

