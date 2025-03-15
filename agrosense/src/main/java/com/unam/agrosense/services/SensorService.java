package com.unam.agrosense.services;

import com.unam.agrosense.model.dispositivo.TipoDispositivo;
import com.unam.agrosense.model.sensor.Sensor;
import com.unam.agrosense.model.sensor.SensorDto;
import com.unam.agrosense.model.sensor.SensorResponseDto;
import com.unam.agrosense.model.tipoSensor.TipoSensor;
import com.unam.agrosense.repository.SensorRepository;
import com.unam.agrosense.repository.TipoSensorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SensorService {

    private final SensorRepository sensorRepository;
    private final TipoSensorRepository tipoSensorRepository;

    public SensorService(SensorRepository sensorRepository, TipoSensorRepository tipoSensorRepository) {
        this.sensorRepository = sensorRepository;
        this.tipoSensorRepository = tipoSensorRepository;
    }


    //Crear un sensor
    @Transactional
    public SensorResponseDto crearSensor(SensorDto sensorDto) {

        List<TipoSensor> tiposDeSensores = tipoSensorRepository.findAllById(sensorDto.idsTipoSensor());

        //Verifico quue el sensor no exista
        if (sensorRepository.existsByNombreAndActivoTrue(sensorDto.nombre())) {
            throw new RuntimeException("El sensor ya existe");
        }

        Sensor sensor = new Sensor();
        sensor.setNombre(sensorDto.nombre());
        sensor.setModelo(sensorDto.modelo());
        sensor.setLatitud(sensorDto.latitud());
        sensor.setLongitud(sensorDto.longitud());
        sensor.setDescripcion(sensorDto.descripcion());
        sensor.setTipoDispositivo(TipoDispositivo.SENSOR);
        sensor.getTiposSensores().addAll(tiposDeSensores);
        tiposDeSensores.forEach(tipoSensor -> tipoSensor.getSensores().add(sensor));


        sensorRepository.save(sensor);

        return new SensorResponseDto(
                sensor.getId(),
                sensor.getNombre(),
                sensor.getModelo(),
                sensor.getLatitud(),
                sensor.getLongitud(),
                sensor.getDescripcion(),
                sensor.getTiposSensores()
        );
    }


    //Actualizar un sensor
    @Transactional
    public SensorResponseDto actualizarSensor(Long id, SensorDto sensorDto) {
        Sensor sensor = sensorRepository.findByIdAndActivoTrue(id)
                .orElseThrow(EntityNotFoundException::new);

        List<TipoSensor> tiposDeSensores = tipoSensorRepository.findAllById(sensorDto.idsTipoSensor());

        sensor.setNombre(sensorDto.nombre());
        sensor.setModelo(sensorDto.modelo());
        sensor.setLatitud(sensorDto.latitud());
        sensor.setLongitud(sensorDto.longitud());
        sensor.setDescripcion(sensorDto.descripcion());
        if (!tiposDeSensores.isEmpty()) {
            sensor.getTiposSensores().forEach(
                    tipoSensor -> tipoSensor.getSensores().remove(sensor)
            );
            sensor.getTiposSensores().clear();

            tiposDeSensores.forEach(tipoSensor -> {
                sensor.getTiposSensores().add(tipoSensor);
                tipoSensor.getSensores().add(sensor);
            });
        }
        sensorRepository.save(sensor);

        return new SensorResponseDto(
                sensor.getId(),
                sensor.getNombre(),
                sensor.getModelo(),
                sensor.getLatitud(),
                sensor.getLongitud(),
                sensor.getDescripcion(),
                sensor.getTiposSensores()
        );
    }

    //Eliminar un sensor
    @Transactional
    public void eliminarSensor(Long id) {
        Sensor sensor = sensorRepository.findByIdAndActivoTrue(id).
                orElseThrow(() -> new EntityNotFoundException("El sensor no existe"));

        sensorRepository.softDelete(id);
        for (TipoSensor tipoSensor : sensor.getTiposSensores()) {
            tipoSensor.getSensores().remove(sensor);
        }
        sensor.getTiposSensores().clear();

    }

    //Obtener un sensor
    public SensorResponseDto obtenerSensor(Long id) {
        Sensor sensor = sensorRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("El sensor no existe"));

        return new SensorResponseDto(
                sensor.getId(),
                sensor.getNombre(),
                sensor.getModelo(),
                sensor.getLatitud(),
                sensor.getLongitud(),
                sensor.getDescripcion(),
                sensor.getTiposSensores()
        );

    }

    //Obtener todos los sensores
    public List<SensorResponseDto> obtenerSensores() {
        List<Sensor> sensores = sensorRepository.findByActivoTrue();
        return sensores.stream()
                .map(sensor -> new SensorResponseDto(
                        sensor.getId(),
                        sensor.getNombre(),
                        sensor.getModelo(),
                        sensor.getLatitud(),
                        sensor.getLongitud(),
                        sensor.getDescripcion(),
                        sensor.getTiposSensores()
                ))
                .toList();
    }

    public int cantidadDeSensores() {
        var sensorCount = 0;
        List<Sensor> sensores =  sensorRepository.findAllByActivoTrue();
        sensorCount = sensores.size();
        return sensorCount;
    }
}
