package com.unam.agrosense.model.datoSensor;

import com.unam.agrosense.model.sensor.Sensor;
import com.unam.agrosense.model.tipoSensor.TipoMedida;
import com.unam.agrosense.model.tipoSensor.TipoSensor;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record DatoSensorDto(

        BigDecimal valor,
        Sensor sensor


) {
}
