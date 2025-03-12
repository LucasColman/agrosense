package com.unam.agrosense.model.datoSensor;

import com.unam.agrosense.model.sensor.Sensor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record DatoSensorDto(
        @NotNull
        Double valor,
        @NotNull
        LocalDateTime fechaHora,
        @NotNull
        Sensor sensor
) {
}
