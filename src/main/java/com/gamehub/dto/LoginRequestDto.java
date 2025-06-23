package com.gamehub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Petición de inicio de sesión con credenciales del usuario.")
public class LoginRequestDto {

    @Schema(description = "Correo electrónico del usuario", example = "user@example.com")
    private String email;

    @Schema(description = "Contraseña del usuario", example = "password123")
    private String password;
}