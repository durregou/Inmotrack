package com.arrendamiento.administracion.repository;

import com.arrendamiento.administracion.entity.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    
    Optional<Administrador> findByCorreo(String correo);
    
    Optional<Administrador> findByCorreoAndContrasena(String correo, String contrasena);
    
    boolean existsByCorreo(String correo);
}
