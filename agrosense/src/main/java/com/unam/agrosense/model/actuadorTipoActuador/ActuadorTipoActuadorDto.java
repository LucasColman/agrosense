package com.unam.agrosense.model.actuadorTipoActuador;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActuadorTipoActuadorDto(

        @NotNull
        Long tipoActuadorId,
        @NotNull
        Long actuadorId,
        @NotBlank
        String estadoActual
) {
}
