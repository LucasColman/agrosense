package com.unam.agrosense.services;

import com.unam.agrosense.model.tipoSensor.TipoMedida;
import com.unam.agrosense.model.tipoSensor.TipoSensor;
import com.unam.agrosense.model.tipoSensor.TipoSensorResponseDto;
import com.unam.agrosense.repository.TipoSensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoSensorService {
    @Autowired
    private TipoSensorRepository tipoSensorRepository;

    public TipoSensor registrarTipoSensor(TipoSensor tipoSensor) {
        return tipoSensorRepository.save(tipoSensor);
    }

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

    public Optional<TipoSensor> obtenerTipoSensor(Long id) {
        return tipoSensorRepository.findById(id);
    }

    public TipoSensor actualizarTipoMedida(Long id, TipoMedida nuevaMedida) {
        return tipoSensorRepository.findById(id).map(tipoSensor -> {
            tipoSensor.setTipoMedida(nuevaMedida);
            return tipoSensorRepository.save(tipoSensor);
        }).orElseThrow(() -> new IllegalArgumentException("Tipo de sensor no encontrado"));
    }

    public void eliminarTipoSensor(Long id) {
        if(!tipoSensorRepository.existsById(id)) {
            throw new RuntimeException("El tipo de sensor no existe");
        }
        tipoSensorRepository.deleteById(id);
    }
}
