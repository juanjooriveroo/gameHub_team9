package com.gamehub.dto;

import com.gamehub.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO que representa un mensaje enviado por un usuario en el contexto de una partida.")
public class MessageMatchDto {
    @Schema(description = "Contenido del mensaje", example = "¡Buena suerte!")
    String message;

    @Schema(description = "Usuario que envió el mensaje")
    User sender;

    @Schema(description = "Fecha y hora del mensaje", example = "2025-06-23T15:45:00")
    LocalDateTime timestamp;
}
