package com.gamehub.service;

import java.util.UUID;

public interface TournamentService {

    Tournament <Tournament> createTournament(Tournament tournament);

    List<Tournament> getAllTournaments();

    Tournament getTournamentById(UUID tournamentId);

    void joinTournament(UUID tournamentId, UUID userId);
}
