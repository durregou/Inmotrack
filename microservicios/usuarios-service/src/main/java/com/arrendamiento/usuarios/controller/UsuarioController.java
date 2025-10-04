package com.arrendamiento.usuarios.controller;

import com.arrendamiento.usuarios.dto.*;
import com.arrendamiento.usuarios.entity.Usuario;
import com.arrendamiento.usuarios.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;
    
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@Valid @RequestBody UsuarioRegistroRequest request) {
        try {
            Usuario usuario = usuarioService.registrarUsuario(request);
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Usuario registrado exitosamente");
            response.put("id", usuario.getId());
            response.put("correo", usuario.getCorreo());
            response.put("tipoUsuario", usuario.getTipoUsuario());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse response = usuarioService.autenticar(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
        }
    }
    
    @PostMapping("/recuperar-password")
    public ResponseEntity<?> solicitarRecuperacion(@Valid @RequestBody RecuperacionRequest request) {
        try {
            String mensaje = usuarioService.solicitarRecuperacionPassword(request);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", mensaje);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @PostMapping("/restablecer-password")
    public ResponseEntity<?> restablecerPassword(@Valid @RequestBody RestablecerPasswordRequest request) {
        try {
            String mensaje = usuarioService.restablecerPassword(request);
            Map<String, String> response = new HashMap<>();
            response.put("mensaje", mensaje);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuario(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.obtenerUsuarioPorId(id);
            return ResponseEntity.ok(usuario);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios(@RequestParam(required = false) String tipo) {
        if (tipo != null) {
            return ResponseEntity.ok(usuarioService.listarUsuariosPorTipo(tipo));
        }
        return ResponseEntity.ok(usuarioService.listarTodosUsuarios());
    }
    
    @PutMapping("/{id}/desactivar")
    public ResponseEntity<?> desactivarUsuario(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.desactivarUsuario(id);
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Usuario desactivado exitosamente");
            response.put("usuario", usuario);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
    
    @PutMapping("/{id}/activar")
    public ResponseEntity<?> activarUsuario(@PathVariable Long id) {
        try {
            Usuario usuario = usuarioService.activarUsuario(id);
            Map<String, Object> response = new HashMap<>();
            response.put("mensaje", "Usuario activado exitosamente");
            response.put("usuario", usuario);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }
}

