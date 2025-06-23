package com.gamehub.model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
@Schema(description = "Entidad que representa a un usuario registrado en el sistema.")
public class User {

	@Id
	@Schema(description = "Identificador único del usuario", example = "a1b2c3d4-e5f6-7890-1234-56789abcdef0")
	private UUID id;

	@Column(unique = true, nullable = false, length = 100)
	@Schema(description = "Nombre de usuario único", example = "gameMaster99")
	private String username;

	@Column(unique = true, nullable = false, length = 100)
	@Schema(description = "Correo electrónico único del usuario", example = "user@example.com")
	private String email;

	@Column(nullable = false, length = 100)
	@Schema(description = "Contraseña cifrada del usuario", example = "$2a$10$...")
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(length = 50)
	@Schema(description = "Rol asignado al usuario", example = "PLAYER")
	private Role role;

	@Column(length = 50)
	@Schema(description = "Rango o clasificación del usuario", example = "Bronze")
	private String rank;

	@Column(nullable = false)
	@Schema(description = "Puntos acumulados del usuario", example = "1200")
	private int points = 0;

	@ManyToMany(mappedBy = "players")
	@Schema(description = "Torneos en los que participa el usuario")
	private List<Tournament> tournaments;
}