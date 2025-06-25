package com.gamehub.controller;

import com.gamehub.dto.tournament.TournamentDto;
import com.gamehub.dto.tournament.TournamentRequestDto;
import com.gamehub.dto.tournament.TournamentResponseDto;
import com.gamehub.exception.TournamentFullException;
import com.gamehub.exception.UnauthorizedException;
import com.gamehub.exception.UserAlreadyJoinedException;
import com.gamehub.model.Role;
import com.gamehub.model.Tournament;
import com.gamehub.model.User;
import com.gamehub.service.TournamentService;
import com.gamehub.service.TournamentServiceImpl;
import com.gamehub.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tournaments")
@RequiredArgsConstructor
@Tag(
        name = "Torneos",
        description = "Endpoints para la gestión de torneos, incluyendo creación, listado y unión a torneos"
)
public class TournamentsController {

    private final TournamentService tournamentService;

    private  final UserService userService;

    @Operation(
            summary = "Crear un torneo",
            description = "Crea un nuevo torneo. Solo los usuarios con rol ADMIN pueden crear torneos.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Torneo creado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TournamentResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado, solo administradores pueden crear torneos",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UnauthorizedException.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Solicitud inválida, datos del torneo incorrectos",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TournamentFullException.class)
                            )
                    )
            }
    )
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TournamentResponseDto> createTournament(@RequestBody TournamentRequestDto request) {
        TournamentResponseDto response = tournamentService.createTournament(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(
            summary = "Listar torneos",
            description = "Obtiene una lista de todos los torneos disponibles.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de torneos obtenida exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TournamentResponseDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TournamentFullException.class)
                            )
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<TournamentResponseDto>> listTournaments() {
        List<TournamentResponseDto> tournaments = tournamentService.getAllTournaments();
        return ResponseEntity.ok(tournaments);
    }

    @Operation(
            summary = "Obtener torneo por ID",
            description = "Obtiene los detalles de un torneo específico por su ID.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Torneo encontrado exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TournamentDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Torneo no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TournamentFullException.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Error interno del servidor",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TournamentFullException.class)
                            )
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity <TournamentDto> getTournamentById (@PathVariable UUID id) {

        TournamentDto tournamentById = tournamentService.getTournamentById(id);

        return ResponseEntity.ok(tournamentById);
    }

    @Operation(
            summary = "Unirse a un torneo",
            description = "Permite a un usuario unirse a un torneo específico. Solo los usuarios con rol PLAYER pueden unirse.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Unión al torneo exitosa",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Acceso denegado, solo jugadores pueden unirse a torneos",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UnauthorizedException.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Torneo no encontrado o el usuario ya se ha unido al torneo",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserAlreadyJoinedException.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Torneo lleno, no se puede unir más jugadores",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = TournamentFullException.class)
                            )
                    )
            }
    )
    @PostMapping("/{id}/join")
    public ResponseEntity<?> joinTournament(@PathVariable UUID id) {
        UUID userId = userService.getCurrentUserEntity().getId();

        tournamentService.joinTournament(id, userId);

        return ResponseEntity.ok("Te has unido al torneo correctamente.");

    }
}
