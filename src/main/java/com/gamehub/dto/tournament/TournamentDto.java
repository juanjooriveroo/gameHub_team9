package com.gamehub.dto.tournament;

import com.gamehub.model.Status;
import com.gamehub.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "DTO que representa un torneo con sus detalles, incluyendo jugadores y estado.")
public class TournamentDto {
    @Schema(description = "Identificador único del torneo", example = "123e4567-e89b-12d3-a456-426614174000")
    private String id;

    @Schema(description = "Nombre del torneo", example = "Campeonato Mundial de Ajedrez")
    private String name;

    @Schema(description = "Descripción del torneo", example = "Un torneo internacional de ajedrez.")
    private int maxPlayers;

    @Schema(description = "Número máximo de jugadores permitidos en el torneo", example = "16")
    private Status status;

    @Schema(description = "Estado actual del torneo", example = "FINISHED")
    private List<User> players;
}
