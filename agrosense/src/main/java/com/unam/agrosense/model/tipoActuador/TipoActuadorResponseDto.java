package com.unam.agrosense.model.tipoActuador;

import com.unam.agrosense.model.actuador.Actuador;

import java.util.List;

public record TipoActuadorResponseDto(
    Long id,
    String descripcion,
    Comportamiento comportamiento,
    boolean activo,
    List<String> estados,
    List<Actuador> actuadores
) {
}
