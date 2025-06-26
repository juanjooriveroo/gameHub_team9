package com.gamehub.exception;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Schema(description = "Respuesta de error estandarizada")
public class ApiErrorResponse {

    @Schema(description = "Fecha y hora del error", example = "2025-06-21T17:33:00")
    private LocalDateTime timestamp;

    @Schema(description = "Código de estado HTTP", example = "Código de estado HTTP")
    private int status;

    @Schema(description = "Descripción del estado HTTP", example = "Descripción del estado HTTP")
    private String error;

    @Schema(description = "Mensaje explicativo del error", example = "Mensaje explicativo del error")
    private String message;
}
