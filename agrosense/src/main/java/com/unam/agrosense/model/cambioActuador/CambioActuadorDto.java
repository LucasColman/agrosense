package com.unam.agrosense.model.cambioActuador;

import com.unam.agrosense.model.actuadorTipoActuador.ActuadorTipoActuadorId;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CambioActuadorDto(
        @NotBlank
        String estadoAnterior,
        @NotBlank
        String estadoNuevo,
        @NotNull
        LocalDateTime fechaCambio,
        @NotNull
        Long actuadorId,

        @NotNull
        Long tipoActuadorId
) {
}
