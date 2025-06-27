package com.gamehub.dto;

import com.gamehub.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Schema(description = "DTO para mensajes genéricos en la aplicación.")
public class MessageDto {

    @Schema(description = "Contenido del mensaje", example = "¡Hola, bienvenido a GameHub!")
    private String content;

    @Schema(description = "Marca de tiempo del mensaje", example = "2023-10-01")
    private LocalDateTime timestamp;

    @Schema(description = "Usuario que envió el mensaje", example = "Manolo")
    private String sender;
}
