package com.unam.agrosense.model.usuario;

public record UsuarioResponseDto(
        Long id,
        String username,
        String email,
        Rol rol
) {
}
