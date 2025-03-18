package com.unam.agrosense.model.datoSensor;

import com.unam.agrosense.model.sensor.Sensor;
import com.unam.agrosense.model.tipoSensor.TipoMedida;
import com.unam.agrosense.model.tipoSensor.TipoSensor;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record DatoSensorResponseDto(
        Long id,
        BigDecimal valor,
        LocalDateTime fecha,
        Long idSensor

) {
}
