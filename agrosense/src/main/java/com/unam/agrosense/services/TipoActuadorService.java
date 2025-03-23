package com.unam.agrosense.services;

import com.unam.agrosense.model.actuador.Actuador;
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
import java.util.stream.Collectors;

@Service
public class TipoActuadorService {
    @Autowired
    private TipoActuadorRepository tipoActuadorRepository;

    @Transactional
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
                        tipo.getEstados()
//                        tipo.getActuadores()
                ))
                .toList();
    }

    public Optional<TipoActuador> obtenerTipoActuador(Long id) {
        return tipoActuadorRepository.findById(id);
    }


    @Transactional
    public TipoActuador actualizarTipoActuador(Long id, TipoActuador tipoActuador) {
        TipoActuador tipoActuadorActual = tipoActuadorRepository.findByIdAndActivoTrue(id).
                orElseThrow(() -> new EntityNotFoundException("El tipo de actuador no existe"));

        tipoActuadorActual.setDescripcion(tipoActuador.getDescripcion());
        tipoActuadorActual.setComportamiento(tipoActuador.getComportamiento());

        List<String> estadosLimpios = tipoActuador.getEstados().stream()
                .map(estado -> estado.replace("[", "").replace("]", "").trim())
                .collect(Collectors.toList());

        tipoActuadorActual.setEstados(estadosLimpios);

        return tipoActuadorRepository.save(tipoActuadorActual);
    }

    @Transactional
    public void eliminarTipoActuador(Long id) {
        TipoActuador tipoActuador = tipoActuadorRepository.findByIdAndActivoTrue(id).
                orElseThrow(() -> new EntityNotFoundException("El tipo de actuador no existe"));

        tipoActuadorRepository.softDelete(id);

//        for (Actuador actuador : tipoActuador.getActuadores()) {
//            actuador.getTiposActuadores().remove(tipoActuador);
//        }
//        tipoActuador.getActuadores().clear();
    }

    public TipoActuador obtenerPorId(Long id) {
        return tipoActuadorRepository.findById(id).orElse(null);
    }
}
