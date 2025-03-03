package com.unam.agrosense.security;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank
        String username,
        @NotBlank
        String password
) {

}
