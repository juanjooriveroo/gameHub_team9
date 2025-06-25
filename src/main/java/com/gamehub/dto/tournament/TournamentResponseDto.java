package com.gamehub.dto.tournament;

import com.gamehub.model.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jdk.jshell.Snippet;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "DTO para la respuesta de un torneo con información básica.")
public class TournamentResponseDto {

    @Schema(description = "ID único del torneo", example = "d3f4a8e6-8e84-4c32-a50e-8b2d8e1f0ac7")
    private UUID id;

    @Schema(description = "Nombre del torneo", example = "Campeonato Mundial de Ajedrez.")
    private String name;

    @Schema(description = "Descripción del torneo", example = "Un torneo internacional de ajedrez.")
    private int maxPlayers;

    @Schema(description = "Estado actual del torneo", example = "CREATED")
    private Status status;
}
