package com.unam.agrosense.model.actuador;

import com.unam.agrosense.model.dispositivo.TipoDispositivo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ActuadorDto(
        @NotBlank
        String nombre,
        @NotBlank
        String modelo,
        @NotBlank
        String latitud,
        @NotBlank
        String longitud,
        @NotBlank
        String descripcion,
        @NotBlank
        String estadoActuador,
        List<Long> idsTipoActuador
) {
}
