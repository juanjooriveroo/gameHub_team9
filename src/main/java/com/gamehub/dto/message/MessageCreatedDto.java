package com.gamehub.dto.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "DTO para la creación de un mensaje.")
public class MessageCreatedDto {

    @Schema(description = "Contenido del mensaje", example = "¡Hola a todos!")
    private String content;

    @Schema(description = "ID del usuario que envía el mensaje", example = "a1b2c3d4-e5f6-7890-1234-56789abcdef0")
    private String sender;

    @Schema(description = "ID del usuario que recibe el mensaje", example = "b1c2d3e4-f5g6-7890-1234-56789abcdef0")
    private String match;

    @Schema(description = "ID del torneo al que pertenece el mensaje", example = "c1d2e3f4-g5h6-7890-1234-56789abcdef0")
    private String tournament;

}
