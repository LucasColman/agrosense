package com.unam.agrosense.model.datoSensor;

import com.unam.agrosense.model.sensor.Sensor;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatoSensorResponseDto(
        Long id,
        Double valor,

        LocalDateTime fechaHora,

        Sensor sensor
) {
}
