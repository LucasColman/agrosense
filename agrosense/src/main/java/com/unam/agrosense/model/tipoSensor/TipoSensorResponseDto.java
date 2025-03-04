package com.unam.agrosense.model.tipoSensor;

import com.unam.agrosense.model.sensor.Sensor;

import java.util.List;

public record TipoSensorResponseDto(
    Long id,
    String nombre,
    TipoMedida tipoMedida,
    boolean activo,
    List<Sensor> sensores
) {
}
