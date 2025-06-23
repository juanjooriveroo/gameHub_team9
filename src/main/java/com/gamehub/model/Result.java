package com.gamehub.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resultado posible de una partida")
public enum Result {
	@Schema(description = "Partida pendiente")
	PENDING,

	@Schema(description = "Ganador: jugador 1")
	PLAYER1_WIN,

	@Schema(description = "Ganador: jugador 2")
	PLAYER2_WIN
}
