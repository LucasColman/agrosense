package com.unam.agrosense.model.actuador;

import com.unam.agrosense.model.dispositivo.TipoDispositivo;
import com.unam.agrosense.model.tipoActuador.TipoActuador;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ActuadorResponseDto(
        Long id,
        String nombre,
        String modelo,
        String latitud,
        String longitud,
        String descripcion,
        List<TipoActuador> tiposDeActuadores
) {
}
