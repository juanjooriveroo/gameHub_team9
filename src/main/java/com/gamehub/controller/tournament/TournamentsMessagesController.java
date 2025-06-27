package com.gamehub.controller.tournament;

import com.gamehub.dto.message.MessageCreatedDto;
import com.gamehub.dto.message.MessageDto;
import com.gamehub.exception.ApiErrorResponse;
import com.gamehub.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tournaments/{id}/messages")
@RequiredArgsConstructor
@Tag(name = "Mensajes del torneo", description = "Endpoints para la gestión de mensajes en torneos")
public class TournamentsMessagesController {

    private final MessageService messageService;

    @Operation(
            summary = "Listar mensajes del torneo",
            description = "Obtiene una lista de mensajes asociados a un torneo específico. Requiere rol de jugador o administrador.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Lista de mensajes obtenida exitosamente",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageDto.class)
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
    @PreAuthorize("hasAnyRole('PLAYER', 'ADMIN')")
    public ResponseEntity<List<MessageDto>> listTournamentMessages(@PathVariable UUID id) {

        List<MessageDto> messages = messageService.findByTournamentIdMessages(id);

        return ResponseEntity.ok(messages);
    }

    @Operation(
            summary = "Enviar mensaje de torneo",
            description = "Envia mensaje de un torneo concreto pasado por PathVariable en formato UUID id y se extrae el usuario que lo manda via Bearer token. Devuelve un codigo 200 unicamente",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Envio exitoso",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageDto.class)
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
                            responseCode = "401",
                            description = "Token no válido o caducado",
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
    @PostMapping
    @PreAuthorize("hasAnyRole('PLAYER', 'ADMIN')")
    public ResponseEntity<?> sendMessageToTournament(@PathVariable UUID id, @RequestBody MessageCreatedDto messageDtoRequest) {

        MessageDto messageDto = messageService.sendTournamentMessage(id, messageDtoRequest);
        return ResponseEntity.ok(messageDto);
    }

}
