package com.gamehub.dto.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "DTO para mensajes genéricos en la aplicación.")
public class MessageDto {

    @Schema(description = "Contenido del mensaje", example = "¡Hola, bienvenido a GameHub!")
    private String content;

    @Schema(description = "Fecha y hora del mensaje", example = "2025-06-23T15:45:00")
    private LocalDateTime timestamp;

    @Schema(description = "Nombre del usuario que envió el mensaje", example = "Manolo")
    private String sender;

}
