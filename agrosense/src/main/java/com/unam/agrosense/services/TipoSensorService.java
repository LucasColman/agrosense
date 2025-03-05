package com.unam.agrosense.services;

import com.unam.agrosense.model.sensor.Sensor;
import com.unam.agrosense.model.tipoSensor.TipoMedida;
import com.unam.agrosense.model.tipoSensor.TipoSensor;
import com.unam.agrosense.model.tipoSensor.TipoSensorResponseDto;
import com.unam.agrosense.repository.TipoSensorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TipoSensorService {
    @Autowired
    private TipoSensorRepository tipoSensorRepository;

    @Transactional
    public TipoSensor registrarTipoSensor(TipoSensor tipoSensor) {
        return tipoSensorRepository.save(tipoSensor);
    }

    @Transactional
    public List<TipoSensorResponseDto> obtenerTiposSensores() {
        List<TipoSensor> tiposSensores = tipoSensorRepository.findByActivoTrue();
        return tiposSensores.stream()
                .map(tipo -> new TipoSensorResponseDto(
                    tipo.getId(),
                    tipo.getNombre(),
                    tipo.getTipoMedida(),
                    tipo.isActivo(),
                    tipo.getSensores()
                ))
                .toList();
    }

    @Transactional
    public Optional<TipoSensor> obtenerTipoSensor(Long id) {
        return tipoSensorRepository.findById(id);
    }


    @Transactional
    public TipoSensor actualizarTipoSensor(Long id, TipoSensor tipoSensor) {
        TipoSensor tipoSensorActual = tipoSensorRepository.findByIdAndActivoTrue(id).
                orElseThrow(() -> new EntityNotFoundException("El tipoDispositivo de sensor no existe"));

        tipoSensorActual.setNombre(tipoSensor.getNombre());
        tipoSensorActual.setTipoMedida(tipoSensor.getTipoMedida());
        tipoSensorActual.setActivo(tipoSensor.isActivo());

        return tipoSensorRepository.save(tipoSensorActual);
    }
    @Transactional
    public void eliminarTipoSensor(Long id) {
        TipoSensor tipoSensor = tipoSensorRepository.findByIdAndActivoTrue(id).
                orElseThrow(() -> new EntityNotFoundException("El tipoDispositivo de sensor no existe"));

        tipoSensorRepository.softDelete(id);

        for (Sensor sensor : tipoSensor.getSensores()) {
            sensor.getTiposSensores().remove(tipoSensor);
        }
        tipoSensor.getSensores().clear();
    }
}
