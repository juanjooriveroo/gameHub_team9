package com.gamehub.service;

import com.gamehub.dto.tournament.TournamentDto;
import com.gamehub.dto.tournament.TournamentRequestDto;
import com.gamehub.dto.tournament.TournamentResponseDto;
import com.gamehub.model.Tournament;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TournamentService {

    TournamentResponseDto createTournament(TournamentRequestDto request);

    List<TournamentResponseDto> getAllTournaments();

    TournamentDto getTournamentById(UUID tournamentId);

    void joinTournament(UUID tournamentId, UUID userId);

    Optional<Tournament> findById(UUID id);
}
