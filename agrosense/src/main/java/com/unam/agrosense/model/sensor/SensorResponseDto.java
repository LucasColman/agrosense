package com.unam.agrosense.model.sensor;

import com.unam.agrosense.model.tipoSensor.TipoSensor;

import java.util.List;

public record SensorResponseDto(
        Long id,
        String nombre,
        String modelo,
        String latitud,
        String longitud,
        String descripcion,
        List<TipoSensor> tiposDeSensores
) {
}
