package com.gamehub.dto;

import com.gamehub.model.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Petición para registrar un nuevo usuario.")
public class RegisterRequestDto {

    @NotBlank
    @Schema(description = "Nombre de usuario", example = "playerOne")
    private String username;

    @NotBlank
    @Email
    @Schema(description = "Correo electrónico del usuario", example = "player@example.com")
    private String email;

    @NotBlank
    @Schema(description = "Contraseña del usuario", example = "securePassword123")
    private String password;

    @NotNull
    @Schema(description = "Rol del usuario", example = "PLAYER")
    private Role role;
}