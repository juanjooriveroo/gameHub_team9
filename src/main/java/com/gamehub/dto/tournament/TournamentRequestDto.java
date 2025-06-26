package com.gamehub.dto.tournament;

import com.gamehub.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "DTO para la solicitud de creación de un torneo.")
public class TournamentRequestDto {

    @Schema(description = "Nombre del torneo", example = "Campeonato Mundial de Ajedrez")
    private String name;

    @Schema(description = "Descripción del torneo", example = "Un torneo internacional de ajedrez.")
    private int maxPlayers;

    @Schema(description = "Estado inicial del torneo", example = "CREATED")
    private Status status;
}
