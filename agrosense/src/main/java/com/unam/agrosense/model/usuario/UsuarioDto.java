package com.unam.agrosense.model.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UsuarioDto(
        @NotBlank
        String username,
        @NotBlank @Email
        String email,
        @NotBlank @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$") // Mínimo ocho caracteres, al menos una letra mayúscula, una letra minúscula y un número
        String password,

        @NotNull
        Rol rol
) {
}
