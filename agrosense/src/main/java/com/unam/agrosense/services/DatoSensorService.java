package com.unam.agrosense.services;

import com.unam.agrosense.model.datoSensor.DatoSensor;
import com.unam.agrosense.model.datoSensor.DatoSensorDto;
import com.unam.agrosense.model.datoSensor.DatoSensorResponseDto;
import com.unam.agrosense.model.sensor.Sensor;
import com.unam.agrosense.repository.DatoSensorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DatoSensorService {
    private final DatoSensorRepository datoSensorRepository;

    public DatoSensorService(DatoSensorRepository datoSensorRepository) {
        this.datoSensorRepository = datoSensorRepository;
    }

    // Crear un nuevo dato de sensor
    @Transactional
    public DatoSensorResponseDto crearDatoSensor(DatoSensorDto datoSensorDto) {
        DatoSensor datoSensor = new DatoSensor();
        datoSensor.setValor(datoSensorDto.valor());
        datoSensor.setFechaHora(datoSensorDto.fechaHora());
        datoSensor.setSensor(datoSensorDto.sensor());
        datoSensorRepository.save(datoSensor);

        return new DatoSensorResponseDto(
                datoSensor.getId(),
                datoSensor.getValor(),
                datoSensor.getFechaHora(),
                datoSensor.getSensor()
        );
    }

    //Actualizar un dato de sensor
    @Transactional
    public DatoSensorResponseDto actualizarDatoSensor(Long id, DatoSensorDto datoSensorDto) {
        DatoSensor datoSensor = datoSensorRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Dato de sensor no encontrado"));

        datoSensor.setValor(datoSensorDto.valor());
        datoSensor.setFechaHora(datoSensorDto.fechaHora());
        datoSensor.setSensor(datoSensorDto.sensor());
        datoSensorRepository.save(datoSensor);

        return new DatoSensorResponseDto(
                datoSensor.getId(),
                datoSensor.getValor(),
                datoSensor.getFechaHora(),
                datoSensor.getSensor()
        );
    }


    //Eliminar un dato de sensor
    @Transactional
    public void eliminarDatoSensor(Long id) {
        DatoSensor datoSensor = datoSensorRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Dato de sensor no encontrado"));

        datoSensorRepository.softDelete(id);
        datoSensor.getSensor().getDatosSensor().remove(datoSensor);
    }

//Obtener un dato de sensor por id
    public DatoSensorResponseDto obtenerDatoSensor(Long id) {
        DatoSensor datoSensor = datoSensorRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("Dato de sensor no encontrado"));

        return new DatoSensorResponseDto(
                datoSensor.getId(),
                datoSensor.getValor(),
                datoSensor.getFechaHora(),
                datoSensor.getSensor()
        );
    }
//Obtener todos los datos de sensor
    public List<DatoSensorResponseDto> obtenerDatosSensor() {
        List<DatoSensor> datosSensor = datoSensorRepository.findAllByActivoTrue();
        return datosSensor.stream()
                .map(datoSensor -> new DatoSensorResponseDto(
                        datoSensor.getId(),
                        datoSensor.getValor(),
                        datoSensor.getFechaHora(),
                        datoSensor.getSensor()
                ))
                .collect(Collectors.toList());
    }

    public List<Sensor> obtenerSensores() {
        List<DatoSensor> datosSensor = datoSensorRepository.findAllByActivoTrue();

        // Obtener los sensores de los datos de sensor
        return datosSensor.stream()
                .map(DatoSensor::getSensor)
                .collect(Collectors.toList());
    }
}


