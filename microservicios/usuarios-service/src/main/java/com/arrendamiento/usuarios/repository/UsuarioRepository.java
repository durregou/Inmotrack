package com.arrendamiento.usuarios.repository;

import com.arrendamiento.usuarios.entity.Usuario;
import com.arrendamiento.usuarios.entity.Usuario.TipoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Optional<Usuario> findByCorreo(String correo);
    
    Optional<Usuario> findByTokenRecuperacion(String token);
    
    List<Usuario> findByTipoUsuario(TipoUsuario tipoUsuario);
    
    List<Usuario> findByActivo(Boolean activo);
    
    boolean existsByCorreo(String correo);
    
    boolean existsByCedula(String cedula);
}

