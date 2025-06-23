package com.gamehub.dto;

import com.gamehub.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO con información completa del usuario para administración o perfil.")
public class UserDto {

    @Schema(description = "ID único del usuario", example = "d3f4a8e6-8e84-4c32-a50e-8b2d8e1f0ac7")
    private UUID id;

    @Schema(description = "Nombre de usuario", example = "gamerX")
    private String username;

    @Schema(description = "Correo electrónico del usuario", example = "gamer@example.com")
    private String email;

    @Schema(description = "Rol del usuario", example = "PLAYER")
    private Role role;

    @Schema(description = "Rango o clasificación", example = "Platinum")
    private String rank;

    @Schema(description = "Puntos acumulados", example = "1250")
    private int points;
}