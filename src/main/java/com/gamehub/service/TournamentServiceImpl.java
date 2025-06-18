package com.gamehub.service;

import com.gamehub.dto.tournament.TournamentRequestDto;
import com.gamehub.dto.tournament.TournamentResponseDto;
import com.gamehub.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TournamentServiceImpl implements TournamentService {

    //Inyección de dependencias mediante constructor
    private final TournamentRepository tournamentRepository;

    public TournamentServiceImpl(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    //Creación de un torneo
    @Override
    public TournamentResponseDto createTournament(TournamentRequestDto dto) {
        Tournament tournament = new Tournament();
        tournament.setId(UUID.randomUUID());
        tournament.setName(dto.getName());
        tournament.setMaxPlayers(dto.getMaxPlayers());
        tournament.setStatus(Status.CREATED);

        Tournament saved = tournamentRepository.save(tournament);

        TournamentResponseDto response = new TournamentResponseDto();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setMaxPlayers(saved.getMaxPlayers());
        response.setStatus(saved.getStatus());

        return  response;

    }

    //Obtener todos los torneos
    @Override
    public List<TournamentResponseDto> getAllTournaments() {
        return tournamentRepository.findAll().stream()
                .map(tournament -> {
                    TournamentResponseDto dto = new TournamentResponseDto();
                    dto.setId(tournament.getId());
                    dto.setName(tournament.getName());
                    dto.setMaxPlayers(tournament.getMaxPlayers());
                    dto.setStatus(tournament.getStatus());
                    return dto;
                })
                .collect(Collectors.toList());
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
