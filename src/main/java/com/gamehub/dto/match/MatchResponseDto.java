package com.gamehub.dto.match;

import java.util.UUID;

import com.gamehub.model.Result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Respuesta con los datos de una partida jugada entre dos jugadores.")
public class MatchResponseDto {

    @Schema(description = "Identificador único de la partida", example = "b3f1c4d2-7f42-4bde-9e0d-6f3e31a54c99")
    private UUID id;

    @Schema(description = "ID del primer jugador", example = "a7c1e2d3-9b44-46a6-bc91-40d1bcb6d91e")
    private UUID playerOneId;

    @Schema(description = "ID del segundo jugador", example = "d2f7c3a1-2e88-41b5-9513-29bc9c58e2c4")
    private UUID playerTwoId;

    @Schema(description = "Resultado de la partida", example = "PLAYER_ONE_WIN")
    private Result result;

    @Schema(description = "ID del torneo al que pertenece la partida", example = "e1a7c6b9-3567-4f2c-a8c4-d834d3f7b0b2")
    private UUID tournamentId;
}