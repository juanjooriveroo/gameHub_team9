package com.gamehub.repository;

import com.gamehub.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MatchRepository extends JpaRepository<Match, UUID> {

    List<Match> findByTournament_Id(UUID tournamentId);

    List<Match> findAllByTournament_Id(UUID tournamentId);

    Match getMatchById(UUID id);
}
