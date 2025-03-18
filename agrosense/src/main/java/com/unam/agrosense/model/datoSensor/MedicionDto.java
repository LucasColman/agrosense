package com.unam.agrosense.model.datoSensor;

import com.unam.agrosense.model.tipoSensor.TipoMedida;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MedicionDto(
        Long idDatoSensor,
        Long idSensor,
        Long idTipoSensor,
        String tipoMedida,

        BigDecimal valor,

        LocalDateTime fecha

) {
}
