package com.gamehub.controller;

import com.gamehub.dto.UserDto;
import com.gamehub.dto.UserPublicDto;
import com.gamehub.exception.ApiErrorResponse;
import com.gamehub.service.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(
        name = "Usuarios",
        description = "Endpoints para visualización de perfiles de usuario y de tu propio usuario"
)
public class UsersController {

    private final UserServiceImpl userService;

    @Operation(
            summary = "Visualización del perfil",
            security = @SecurityRequirement(name = "bearerAuth"),
            description = "Visualización del perfil del usuario del token enviado en Bearer via POST y devuelve un JSON con info de tu perfil",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Visualización exitosa",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuario no encontrado",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ApiErrorResponse.class))
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
    @PostMapping("/me")
    @PreAuthorize("hasAnyRole('PLAYER', 'ADMIN')")
    public ResponseEntity<UserDto> getCurrentUser(){
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @Operation(
            summary = "Visualización de perfil público de cualquier usuario",
            description = "Visualización del perfil público del usuario del UUID proporcionado en PathVariable via POST y devuelve un JSON con info de su perfil",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Visualización exitosa",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Usuario no encontrado",
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
    @PostMapping("/{id}")
    public ResponseEntity<UserPublicDto> getUserProfile(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }
}