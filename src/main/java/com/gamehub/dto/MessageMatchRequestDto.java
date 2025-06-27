package com.gamehub.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para la solicitud de envío de un mensaje.")
public class MessageMatchRequestDto {
    @Schema(description = "Contenido del mensaje", example = "¡Buena suerte!")
    private String content;
}
