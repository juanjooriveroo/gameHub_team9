package com.gamehub.controller.matches;

import com.gamehub.dto.MessageMatchListDto;
import com.gamehub.dto.MessageMatchRequestDto;
import com.gamehub.exception.ApiErrorResponse;
import com.gamehub.service.MessageMatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/matches/{id}/messages")
@Tag(
        name = "Mensajes de partidos",
        description = "Endpoints para visualizar y enviar mensajes de partidos concretos"
)
public class MatchesMessagesController {

    private final MessageMatchService messageMatchService;

    @Operation(
            summary = "Visualizar mensajes de partida",
            description = "Listar mensajes de una partida concreta pasado por PathVariable en formato UUID id. Devuelve un JSON con la lista ordenada por fecha de envio",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Visualización exitosa",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = MessageMatchListDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "No hay mensajes de este partido",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class))
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
    @GetMapping
    public ResponseEntity<MessageMatchListDto> listMatchMessages(@PathVariable UUID id) {
        MessageMatchListDto messageMatchListDto = messageMatchService.getListMatchMessages(id);
        return ResponseEntity.ok(messageMatchListDto);
    }

    @Operation(
            summary = "Enviar mensaje de partida",
            description = "Envia mensaje de una partida concreta pasado por PathVariable en formato UUID id y se extrae el usuario que lo manda via Bearer token. Devuelve un codigo 200 unicamente",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Envio exitoso"
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
    public ResponseEntity<?> sendMessageToMatch(@PathVariable UUID id, @RequestBody MessageMatchRequestDto messageRequestDto) {
        messageMatchService.sendMessageToMatch(id, messageRequestDto);
        return ResponseEntity.ok().build();
    }
}