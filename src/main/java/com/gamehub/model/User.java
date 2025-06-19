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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users")
public class User {

	@Id
	private UUID id;
	
	@Column(unique=true, nullable= false, length = 100)
	private String username;
	
	@Column(unique=true, nullable= false, length = 100)
	private String email;
	
	@Column(nullable = false, length = 100)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(length = 50)
	private Role role;
	
	@Column(length = 50)
	private String rank;
	
	@Column(nullable = false)
	private int points = 0;
	
	@ManyToMany(mappedBy = "players")
	private List<Tournament> tournaments;
	
}
