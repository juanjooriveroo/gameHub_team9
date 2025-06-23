package com.gamehub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Respuesta que contiene el token JWT después de una autenticación exitosa.")
public class AuthResponseDto {

    @Schema(description = "Token de autenticación JWT generado", example = "eyJhbGciOiJIUzI1NiIsIn...")
    private String token;
}