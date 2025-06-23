package com.gamehub.dto;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Petición para registrar un nuevo usuario.")
public class RegisterRequestDto {

    @Schema(description = "Nombre de usuario", example = "playerOne")
    private String username;

    @Schema(description = "Correo electrónico del usuario", example = "player@example.com")
    private String email;

    @Schema(description = "Contraseña del usuario", example = "securePassword123")
    private String password;
}