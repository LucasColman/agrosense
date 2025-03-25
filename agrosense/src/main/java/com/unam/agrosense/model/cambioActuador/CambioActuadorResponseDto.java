package com.unam.agrosense.model.cambioActuador;

import java.time.LocalDateTime;

public record CambioActuadorResponseDto(

        Long id,
        String estadoAnterior,
        String estadoNuevo,
        LocalDateTime fechaCambio,
        String actuador,
        String tipoActuador

) {
}
