package com.gamehub.dto;

import lombok.Data;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "DTO público con información visible del usuario.")
public class UserPublicDto {

    @Schema(description = "Nombre de usuario", example = "fastGamer")
    private String username;

    @Schema(description = "Rango del jugador", example = "Gold")
    private String rank;

    @Schema(description = "Puntos del jugador", example = "980")
    private int points;
}