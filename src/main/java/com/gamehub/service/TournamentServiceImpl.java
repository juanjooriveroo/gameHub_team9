package com.gamehub.service;

import com.gamehub.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TournamentServiceImpl implements TournamentService {

    //Inyección de dependencias mediante constructor
    private final TournamentRepository tournamentRepository;

    public TournamentServiceImpl(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    //Creación de un torneo
    @Override
    public  Tournament createTournament(TournamentDto dto) {
        Tournament tournament = new Tournament();
        tournament.setName(dto.getName());
        tournament.setDate(dto.getDate());
        return tournamentRepository.save(tournament);
    }

    //Obtener todos los torneos
    @Override
    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    //Obtener un torneo por ID
    @Override
    public Tournament getTournamentById(UUID id) {
        return tournamentRepository.findById(id)
                .orElseThrow(() -> new TournamentNotFoundException(id));
    }

    //Unirse a un torneo
    @Override
    public void joinTournament(UUID tournamentId, UUID userId) {
        Tournament tournament = getTournamentById(tournamentId);
        if (tournament.getParticipants().contains(userId)) {
            throw new UserAlreadyJoinedException(userId, tournamentId);
        }
        tournament.getParticipants().add(userId);
        tournamentRepository.save(tournament);
    }

}
