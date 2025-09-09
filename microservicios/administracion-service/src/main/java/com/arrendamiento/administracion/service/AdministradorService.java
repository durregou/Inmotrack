package com.arrendamiento.administracion.service;

import com.arrendamiento.administracion.dto.LoginRequest;
import com.arrendamiento.administracion.dto.LoginResponse;
import com.arrendamiento.administracion.entity.Administrador;
import com.arrendamiento.administracion.repository.AdministradorRepository;
import com.arrendamiento.administracion.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepository administradorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest loginRequest) {
        // Buscar administrador por correo
        Optional<Administrador> adminOptional = administradorRepository.findByCorreo(loginRequest.getCorreo());
        
        if (adminOptional.isEmpty()) {
            throw new RuntimeException("Credenciales inválidas");
        }
        
        Administrador admin = adminOptional.get();
        
        // Verificar contraseña
        if (!passwordEncoder.matches(loginRequest.getContrasena(), admin.getContrasena())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        
        // Verificar que esté activo
        if (!admin.getActivo()) {
            throw new RuntimeException("Cuenta desactivada");
        }
        
        // Generar token JWT
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getCorreo(),
                loginRequest.getContrasena()
            )
        );
        
        String jwt = tokenProvider.generateToken(authentication);
        
        return new LoginResponse(jwt, admin.getId(), admin.getNombre(), admin.getCorreo());
    }

    public Optional<Administrador> findById(Long id) {
        return administradorRepository.findById(id);
    }

    public Administrador save(Administrador administrador) {
        // Encriptar contraseña antes de guardar
        administrador.setContrasena(passwordEncoder.encode(administrador.getContrasena()));
        return administradorRepository.save(administrador);
    }
}
