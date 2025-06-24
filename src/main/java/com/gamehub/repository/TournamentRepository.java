package com.gamehub.repository;

import com.gamehub.model.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TournamentRepository extends JpaRepository<Tournament, UUID> {

}
