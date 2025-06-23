package com.gamehub.model;

import java.util.List;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "tournaments")
@Schema(description = "Representa un torneo donde compiten varios usuarios.")
public class Tournament {

	@Id
	@Schema(description = "ID único del torneo", example = "f4a8b765-9d0e-4d19-994b-7e3d3e4898a5")
	private UUID id;

	@Column(nullable = false, length = 100)
	@Schema(description = "Nombre del torneo", example = "Spring Championship")
	private String name;

	@Column(name = "max_players", nullable = false)
	@Schema(description = "Número máximo de jugadores", example = "8")
	private int maxplayers;

	@Enumerated(EnumType.STRING)
	@Column(length = 50, nullable = false)
	@Schema(description = "Estado actual del torneo", example = "CREATED")
	private Status status;

	@ManyToMany
	@JoinTable(
			name = "tournament_players",
			joinColumns = @JoinColumn(name = "tournament_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	@Schema(description = "Lista de jugadores inscritos en el torneo")
	private List<User> players;

	@OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
	@Schema(description = "Partidas asociadas al torneo")
	private List<Match> matches;

	@OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
	@Schema(description = "Mensajes globales del torneo")
	private List<Message> messages;
}