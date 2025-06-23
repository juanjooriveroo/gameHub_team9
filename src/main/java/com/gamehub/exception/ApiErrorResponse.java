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

    @Schema(description = "Código de estado HTTP", example = "400")
    private int status;

    @Schema(description = "Descripción del estado HTTP", example = "Bad Request")
    private String error;

    @Schema(description = "Mensaje explicativo del error", example = "El email ya está en uso")
    private String message;
}
