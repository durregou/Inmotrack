package com.arrendamiento.inmuebles.service;

import com.arrendamiento.inmuebles.dto.InmuebleRequest;
import com.arrendamiento.inmuebles.entity.Inmueble;
import com.arrendamiento.inmuebles.repository.InmuebleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InmuebleService {

    @Autowired
    private InmuebleRepository inmuebleRepository;

    public Inmueble registrarInmueble(InmuebleRequest request) {
        Inmueble inmueble = new Inmueble();
        inmueble.setPropietarioId(request.getPropietarioId());
        inmueble.setTipo(request.getTipo());
        inmueble.setDireccion(request.getDireccion());
        inmueble.setCiudad(request.getCiudad());
        inmueble.setDepartamento(request.getDepartamento());
        inmueble.setArea(request.getArea());
        inmueble.setHabitaciones(request.getHabitaciones());
        inmueble.setBanos(request.getBanos());
        inmueble.setParqueaderos(request.getParqueaderos());
        inmueble.setPrecioArriendo(request.getPrecioArriendo());
        inmueble.setPrecioAdministracion(request.getPrecioAdministracion());
        inmueble.setDescripcion(request.getDescripcion());
        inmueble.setAmoblado(request.getAmoblado());
        inmueble.setDisponible(request.getDisponible());
        
        return inmuebleRepository.save(inmueble);
    }

    public List<Inmueble> listarInmuebles() {
        return inmuebleRepository.findDisponiblesYActivos();
    }

    public List<Inmueble> listarTodosLosInmuebles() {
        return inmuebleRepository.findAll();
    }

    public Optional<Inmueble> obtenerPorId(Long id) {
        return inmuebleRepository.findById(id);
    }

    public List<Inmueble> obtenerPorPropietario(Long propietarioId) {
        return inmuebleRepository.findByPropietarioId(propietarioId);
    }

    public List<Inmueble> obtenerDisponibles() {
        return inmuebleRepository.findByDisponibleTrue();
    }

    public List<Inmueble> obtenerPorTipo(String tipo) {
        return inmuebleRepository.findByTipo(tipo);
    }

    public List<Inmueble> obtenerPorCiudad(String ciudad) {
        return inmuebleRepository.findByCiudad(ciudad);
    }

    public Inmueble actualizarDisponibilidad(Long id, Boolean disponible) {
        Optional<Inmueble> inmuebleOpt = inmuebleRepository.findById(id);
        if (inmuebleOpt.isPresent()) {
            Inmueble inmueble = inmuebleOpt.get();
            inmueble.setDisponible(disponible);
            return inmuebleRepository.save(inmueble);
        }
        throw new RuntimeException("Inmueble no encontrado");
    }
}
