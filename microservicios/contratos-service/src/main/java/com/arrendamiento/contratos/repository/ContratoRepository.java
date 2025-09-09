package com.arrendamiento.contratos.repository;

import com.arrendamiento.contratos.entity.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    
    List<Contrato> findByPropietarioId(Long propietarioId);
    
    List<Contrato> findByArrendatarioId(Long arrendatarioId);
    
    List<Contrato> findByInmuebleId(Long inmuebleId);
    
    List<Contrato> findByEstado(Contrato.EstadoContrato estado);
    
    List<Contrato> findByActivoTrue();
    
    @Query("SELECT c FROM Contrato c WHERE c.fechaFin < :fecha AND c.estado = 'ACTIVO'")
    List<Contrato> findContratosVencidos(@Param("fecha") LocalDate fecha);
    
    @Query("SELECT c FROM Contrato c WHERE c.fechaFin BETWEEN :fechaInicio AND :fechaFin AND c.estado = 'ACTIVO'")
    List<Contrato> findContratosPorVencer(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
    
    @Query("SELECT c FROM Contrato c WHERE c.inmuebleId = :inmuebleId AND c.estado = 'ACTIVO'")
    List<Contrato> findContratosActivosPorInmueble(@Param("inmuebleId") Long inmuebleId);
    
    boolean existsByInmuebleIdAndEstado(Long inmuebleId, Contrato.EstadoContrato estado);
}
