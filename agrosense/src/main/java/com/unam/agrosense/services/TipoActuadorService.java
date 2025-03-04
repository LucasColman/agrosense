package com.unam.agrosense.services;

import com.unam.agrosense.model.sensor.Sensor;
import com.unam.agrosense.model.tipoActuador.Comportamiento;
import com.unam.agrosense.model.tipoActuador.TipoActuador;
import com.unam.agrosense.model.tipoActuador.TipoActuadorResponseDto;
import com.unam.agrosense.model.tipoSensor.TipoSensor;
import com.unam.agrosense.repository.TipoActuadorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TipoActuadorService {
    @Autowired
    private TipoActuadorRepository tipoActuadorRepository;

    public TipoActuador registrarTipoActuador(TipoActuador tipoActuador) {
        return tipoActuadorRepository.save(tipoActuador);
    }

    public List<TipoActuadorResponseDto> obtenerTiposActuadores() {
        List<TipoActuador> tiposActuadores = tipoActuadorRepository.findByActivoTrue();
        return tiposActuadores.stream()
                .map(tipo -> new TipoActuadorResponseDto(
                        tipo.getId(),
                        tipo.getDescripcion(),
                        tipo.getComportamiento(),
                        tipo.isActivo(),
                        tipo.getActuadores()
                ))
                .toList();
    }

    public Optional<TipoActuador> obtenerTipoActuador(Long id) {
        return tipoActuadorRepository.findById(id);
    }

    public TipoActuador actualizarComportamiento(Long id, Comportamiento nuevoComportamiento) {
        return tipoActuadorRepository.findById(id).map(tipoActuador -> {
            tipoActuador.setComportamiento(nuevoComportamiento);
            return tipoActuadorRepository.save(tipoActuador);
        }).orElseThrow(() -> new IllegalArgumentException("Tipo de actuador no encontrado"));
    }

    @Transactional
    public void eliminarTipoActuador(Long id) {
        TipoActuador tipoActuador = tipoActuadorRepository.findByIdAndActivoTrue(id).
                orElseThrow(() -> new EntityNotFoundException("El sensor no existe"));

        tipoActuador.getActuadores().clear();
        tipoActuadorRepository.save(tipoActuador);
        
        tipoActuadorRepository.softDelete(id);

    }

}
