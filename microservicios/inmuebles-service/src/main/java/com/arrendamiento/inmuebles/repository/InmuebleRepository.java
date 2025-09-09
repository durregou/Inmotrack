package com.arrendamiento.inmuebles.repository;

import com.arrendamiento.inmuebles.entity.Inmueble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface InmuebleRepository extends JpaRepository<Inmueble, Long> {
    
    List<Inmueble> findByPropietarioId(Long propietarioId);
    
    List<Inmueble> findByDisponibleTrue();
    
    List<Inmueble> findByActivoTrue();
    
    List<Inmueble> findByTipo(String tipo);
    
    List<Inmueble> findByCiudad(String ciudad);
    
    @Query("SELECT i FROM Inmueble i WHERE i.precioArriendo BETWEEN :precioMin AND :precioMax")
    List<Inmueble> findByPrecioArriendoBetween(@Param("precioMin") BigDecimal precioMin, @Param("precioMax") BigDecimal precioMax);
    
    @Query("SELECT i FROM Inmueble i WHERE i.disponible = true AND i.activo = true")
    List<Inmueble> findDisponiblesYActivos();
}
