package com.gamehub.dto.message;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.UUID;

@Data
@Schema(description = "DTO para la creación de un mensaje.")
public class MessageCreatedDto {

    @Schema(description = "Contenido del mensaje", example = "¡Hola a todos!")
    private String content;
}
