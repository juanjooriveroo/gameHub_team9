package com.gamehub.controller;

import com.gamehub.dto.RankingDto;
import com.gamehub.exception.ApiErrorResponse;
import com.gamehub.service.RankingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/tournaments/{id}/ranking")
@RequiredArgsConstructor
@Tag(
        name = "Ranking del torneo",
        description = "Endpoint para la visualizacion del ranking de torneos"
)
public class RankingController {

    private final RankingService rankingService;

    @Operation(
            summary = "Ranking de jugadores del torneo",
            description = "Visualización del ranking de usuarios del torneo. Se pasa mediante PathVariable el id del torneo y devuelve un JSON con una lista ordenada por puntuación. Por victoria te otorgan +3 puntos, por derrota -1 punto.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Visualización exitosa",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = RankingDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Torneo no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<?> rankingTournament(@PathVariable UUID id) {
        RankingDto ranking = rankingService.getRanking(id);
        return ResponseEntity.ok(ranking);
    }
}
