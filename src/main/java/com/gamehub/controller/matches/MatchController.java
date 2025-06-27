package com.gamehub.controller.matches;

import com.gamehub.dto.match.*;
import com.gamehub.dto.tournament.TournamentDto;
import com.gamehub.exception.ApiErrorResponse;
import com.gamehub.service.MatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matches")
@Tag(
        name = "Partidos y emparejamientos",
        description = "Endpoints para la creacion de emparejamientos aleatorios por rondas y asignacion de ganadores"
)
public class MatchController {

    private final MatchService matchService;

    @Operation(
            summary = "Emparejamientos aleatorios",
            description = "Crea partidos de forma aleatoria por rondas. SOLO PUEDEN ADMINS",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Emparejamiento creado exitosamente"
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
    @PostMapping("/generate/{tournamentId}")
    public ResponseEntity<Void> generateMatches(@PathVariable UUID tournamentId) {
        matchService.generateMatches(tournamentId);
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Visualización de partido",
            description = "Visualizar un partido concreto aportando su UUID. Devuelve en formato JSON los datos del partido",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Visualización exitosa"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Partido no encontrado",
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
    @GetMapping("/{id}")
    public ResponseEntity<MatchResponseDto> getMatch(@PathVariable UUID id) {
        return ResponseEntity.ok(matchService.getMatchById(id));
    }

    @Operation(
            summary = "Actualización del resultado de partido",
            description = "Actualiza el resultado de un partido decidiendo quien es su ganador. Se le pasa el UUID del partido y en formato JSON el valor de victoria (PLAYER1_WIN o PLAYER2_WIN)",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Actualización exitosa"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Partido no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Se está enviando una entrada incorrecta (un valor inválido para el enum).",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class))
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
    @PutMapping("/{id}/result")
    public ResponseEntity<Void> updateResult(@PathVariable UUID id, @RequestBody MatchResultUpdateDto dto) {
        matchService.updateMatchResult(id, dto);
        return ResponseEntity.ok().build();
    }
}
