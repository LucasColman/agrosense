package com.unam.agrosense.model.cambioActuador;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public record CambioActuadorDto(
        @NotBlank
        String estadoAnterior,
        @NotBlank
        String estadoNuevo,
        @NotBlank
        LocalDateTime fechaCambio,
        @NotBlank
        Long actuadorId
) {
}
