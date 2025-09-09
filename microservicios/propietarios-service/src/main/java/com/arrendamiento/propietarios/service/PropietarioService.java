package com.arrendamiento.propietarios.service;

import com.arrendamiento.propietarios.dto.PropietarioRequest;
import com.arrendamiento.propietarios.dto.PropietarioResponse;
import com.arrendamiento.propietarios.entity.Propietario;
import com.arrendamiento.propietarios.repository.PropietarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropietarioService {

    @Autowired
    private PropietarioRepository propietarioRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public PropietarioResponse registrarPropietario(PropietarioRequest request) {
        // Validar que el correo no esté registrado
        if (propietarioRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("El correo electrónico ya está registrado");
        }
        
        // Validar que la cédula no esté registrada (si se proporciona)
        if (request.getCedula() != null && !request.getCedula().trim().isEmpty()) {
            if (propietarioRepository.existsByCedula(request.getCedula())) {
                throw new RuntimeException("La cédula ya está registrada");
            }
        }
        
        // Crear nuevo propietario
        Propietario propietario = new Propietario();
        propietario.setNombre(request.getNombre());
        propietario.setApellido(request.getApellido());
        propietario.setCorreo(request.getCorreo());
        propietario.setContrasena(passwordEncoder.encode(request.getContrasena()));
        propietario.setTelefono(request.getTelefono());
        propietario.setDireccion(request.getDireccion());
        propietario.setCedula(request.getCedula());
        
        Propietario saved = propietarioRepository.save(propietario);
        
        return convertToResponse(saved);
    }

    public Optional<PropietarioResponse> obtenerPorId(Long id) {
        return propietarioRepository.findById(id)
                .map(this::convertToResponse);
    }

    private PropietarioResponse convertToResponse(Propietario propietario) {
        return new PropietarioResponse(
            propietario.getId(),
            propietario.getNombre(),
            propietario.getApellido(),
            propietario.getCorreo(),
            propietario.getTelefono(),
            propietario.getDireccion(),
            propietario.getCedula(),
            propietario.getFechaRegistro(),
            propietario.getActivo()
        );
    }
}
