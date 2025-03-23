package com.unam.agrosense.model.actuadorTipoActuador;

public record ActuadorTipoActuadorResponseDto(
        Long tipoActuadorId,
        Long actuadorId,
        String estadoActual,
        String descripcion
) {
}
