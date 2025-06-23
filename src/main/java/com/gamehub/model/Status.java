package com.gamehub.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Estado actual de un torneo")
public enum Status {
	@Schema(description = "El torneo está creado pero no ha comenzado")
	CREATED,

	@Schema(description = "El torneo está en progreso")
	IN_PROGRESS,

	@Schema(description = "El torneo ha finalizado")
	FINISHED
}
