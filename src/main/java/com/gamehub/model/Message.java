package com.gamehub.model;

import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.ForeignKey;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="messages")
@Schema(description = "Mensaje enviado por un usuario en el contexto de un torneo o partida.")
public class Message {

	@Id
	@Schema(description = "ID único del mensaje", example = "123e4567-e89b-12d3-a456-426614174000")
	private UUID id;

	@Column(nullable = false)
	@Schema(description = "Contenido del mensaje", example = "¡Buena suerte!")
	private String content;

	@Column(nullable = false)
	@Schema(description = "Fecha y hora del mensaje", example = "2025-06-23T15:45:00")
	private LocalDateTime timestamp;

	@ManyToOne
	@JoinColumn(name = "sender_id", foreignKey = @ForeignKey(name = "fk_message_sender"))
	@Schema(description = "Usuario que envió el mensaje")
	private User sender;

	@ManyToOne
	@JoinColumn(name = "match_id", foreignKey = @ForeignKey(name = "fk_message_match"))
	@Schema(description = "Partida en la que se envió el mensaje")
	private Match match;

	@ManyToOne
	@JoinColumn(name = "tournament_id", foreignKey = @ForeignKey(name = "fk_message_tournament"))
	@Schema(description = "Torneo en el que se envió el mensaje")
	private Tournament tournament;
}