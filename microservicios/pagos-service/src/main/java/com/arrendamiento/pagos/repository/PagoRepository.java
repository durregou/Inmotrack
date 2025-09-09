package com.arrendamiento.pagos.repository;

import com.arrendamiento.pagos.entity.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PagoRepository extends JpaRepository<Pago, Long> {
    
    List<Pago> findByContratoId(Long contratoId);
    
    List<Pago> findByArrendatarioId(Long arrendatarioId);
    
    List<Pago> findByEstado(Pago.EstadoPago estado);
    
    List<Pago> findByTipo(Pago.TipoPago tipo);
    
    @Query("SELECT p FROM Pago p WHERE p.contratoId = :contratoId ORDER BY p.mesCorrespondiente DESC")
    List<Pago> findByContratoIdOrderByMesDesc(@Param("contratoId") Long contratoId);
    
    @Query("SELECT p FROM Pago p WHERE p.fechaVencimiento < :fecha AND p.estado IN ('PENDIENTE', 'PARCIAL')")
    List<Pago> findPagosVencidos(@Param("fecha") LocalDate fecha);
    
    @Query("SELECT p FROM Pago p WHERE p.fechaVencimiento BETWEEN :fechaInicio AND :fechaFin AND p.estado IN ('PENDIENTE', 'PARCIAL')")
    List<Pago> findPagosPorVencer(@Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);
    
    @Query("SELECT p FROM Pago p WHERE p.contratoId = :contratoId AND p.mesCorrespondiente = :mes")
    List<Pago> findByContratoIdAndMes(@Param("contratoId") Long contratoId, @Param("mes") LocalDate mes);
    
    @Query("SELECT p FROM Pago p WHERE EXTRACT(YEAR FROM p.fecha) = :year AND EXTRACT(MONTH FROM p.fecha) = :month")
    List<Pago> findPagosByYearAndMonth(@Param("year") int year, @Param("month") int month);
    
    @Query("SELECT COUNT(p) FROM Pago p WHERE p.contratoId = :contratoId AND p.estado = 'PENDIENTE'")
    Long countPagosPendientesByContrato(@Param("contratoId") Long contratoId);
}
