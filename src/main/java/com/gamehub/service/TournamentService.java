package com.gamehub.service;

import com.gamehub.dto.tournament.TournamentRequestDto;
import com.gamehub.dto.tournament.TournamentResponseDto;

import java.util.List;
import java.util.UUID;

public interface TournamentService {

    TournamentResponseDto createTournament(TournamentRequestDto request);

    List<TournamentResponseDto> getAllTournaments();

    Tournament getTournamentById(UUID tournamentId);

    void joinTournament(UUID tournamentId, UUID userId);
}
