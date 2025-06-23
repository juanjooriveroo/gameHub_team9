package com.gamehub.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Rol que puede tener un usuario")
public enum Role {
	@Schema(description = "Administrador del sistema")
	ADMIN,

	@Schema(description = "Jugador regular")
	PLAYER
}
