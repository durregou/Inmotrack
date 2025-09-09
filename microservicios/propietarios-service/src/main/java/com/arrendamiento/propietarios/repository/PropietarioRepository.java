package com.arrendamiento.propietarios.repository;

import com.arrendamiento.propietarios.entity.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropietarioRepository extends JpaRepository<Propietario, Long> {
    
    Optional<Propietario> findByCorreo(String correo);
    
    boolean existsByCorreo(String correo);
    
    boolean existsByCedula(String cedula);
}
