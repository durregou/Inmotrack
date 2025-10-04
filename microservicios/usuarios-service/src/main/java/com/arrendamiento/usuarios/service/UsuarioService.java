package com.arrendamiento.usuarios.service;

import com.arrendamiento.usuarios.dto.*;
import com.arrendamiento.usuarios.entity.Usuario;
import com.arrendamiento.usuarios.repository.UsuarioRepository;
import com.arrendamiento.usuarios.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired(required = false)
    private JavaMailSender mailSender;
    
    public Usuario registrarUsuario(UsuarioRegistroRequest request) {
        // Validar si el correo ya existe
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        
        // Validar si la cédula ya existe
        if (request.getCedula() != null && usuarioRepository.existsByCedula(request.getCedula())) {
            throw new RuntimeException("La cédula ya está registrada");
        }
        
        Usuario usuario = new Usuario();
        usuario.setCorreo(request.getCorreo());
        usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setTelefono(request.getTelefono());
        usuario.setCedula(request.getCedula());
        usuario.setTipoUsuario(request.getTipoUsuario());
        usuario.setActivo(true);
        
        return usuarioRepository.save(usuario);
    }
    
    public LoginResponse autenticar(LoginRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(request.getCorreo());
        
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }
        
        Usuario usuario = usuarioOpt.get();
        
        if (!usuario.getActivo()) {
            throw new RuntimeException("Usuario inactivo");
        }
        
        if (!passwordEncoder.matches(request.getContrasena(), usuario.getContrasena())) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        
        String token = jwtUtil.generateToken(
            usuario.getCorreo(),
            usuario.getTipoUsuario().name(),
            usuario.getId()
        );
        
        return new LoginResponse(
            usuario.getId(),
            usuario.getCorreo(),
            usuario.getNombre(),
            usuario.getApellido(),
            usuario.getTipoUsuario().name(),
            token,
            "Autenticación exitosa"
        );
    }
    
    public String solicitarRecuperacionPassword(RecuperacionRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(request.getCorreo());
        
        if (usuarioOpt.isEmpty()) {
            // Por seguridad, no revelamos si el correo existe o no
            return "Si el correo existe, recibirás un mensaje con instrucciones";
        }
        
        Usuario usuario = usuarioOpt.get();
        
        // Generar token único
        String token = UUID.randomUUID().toString();
        usuario.setTokenRecuperacion(token);
        usuario.setTokenExpiracion(LocalDateTime.now().plusHours(24));
        
        usuarioRepository.save(usuario);
        
        // Enviar correo (si está configurado)
        try {
            if (mailSender != null) {
                enviarCorreoRecuperacion(usuario.getCorreo(), token);
            }
        } catch (Exception e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
        }
        
        return "Si el correo existe, recibirás un mensaje con instrucciones";
    }
    
    public String restablecerPassword(RestablecerPasswordRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByTokenRecuperacion(request.getToken());
        
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Token inválido");
        }
        
        Usuario usuario = usuarioOpt.get();
        
        if (usuario.getTokenExpiracion().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado");
        }
        
        usuario.setContrasena(passwordEncoder.encode(request.getNuevaContrasena()));
        usuario.setTokenRecuperacion(null);
        usuario.setTokenExpiracion(null);
        
        usuarioRepository.save(usuario);
        
        return "Contraseña restablecida exitosamente";
    }
    
    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }
    
    public List<Usuario> listarUsuariosPorTipo(String tipo) {
        Usuario.TipoUsuario tipoUsuario = Usuario.TipoUsuario.valueOf(tipo.toUpperCase());
        return usuarioRepository.findByTipoUsuario(tipoUsuario);
    }
    
    public List<Usuario> listarTodosUsuarios() {
        return usuarioRepository.findAll();
    }
    
    public Usuario desactivarUsuario(Long id) {
        Usuario usuario = obtenerUsuarioPorId(id);
        usuario.setActivo(false);
        return usuarioRepository.save(usuario);
    }
    
    public Usuario activarUsuario(Long id) {
        Usuario usuario = obtenerUsuarioPorId(id);
        usuario.setActivo(true);
        return usuarioRepository.save(usuario);
    }
    
    private void enviarCorreoRecuperacion(String correo, String token) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(correo);
        mensaje.setSubject("Recuperación de Contraseña - Sistema de Arrendamiento");
        mensaje.setText("Para restablecer tu contraseña, usa el siguiente token:\n\n" +
                       token + "\n\n" +
                       "Este token expira en 24 horas.");
        
        mailSender.send(mensaje);
    }
}

