package com.gamehub.dto.match;

import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "DTO para actualizar el resultado de una partida.")
public class MatchResultUpdateDto {

    @Schema(description = "Resultado actualizado de la partida. Valores posibles: PLAYER1_WIN o PLAYER2_WIN",
            example = "PLAYER1_WIN")
    private String result;
}