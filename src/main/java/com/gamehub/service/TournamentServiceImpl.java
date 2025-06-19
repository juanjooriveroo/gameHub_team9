package com.gamehub.service;

import com.gamehub.dto.tournament.TournamentDto;
import com.gamehub.dto.tournament.TournamentRequestDto;
import com.gamehub.dto.tournament.TournamentResponseDto;
import com.gamehub.mapper.TournamentMapper;
import com.gamehub.repository.TournamentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;

    private final TournamentMapper tournamentMapper;

    //Creación de un torneo
    @Override
    public TournamentResponseDto createTournament(TournamentRequestDto dto) {
        Tournament tournament = tournamentMapper.toEntity(dto);
        Tournament saved = tournamentRepository.save(tournament);
        return tournamentMapper.toDto(saved);
    }

    //Obtener todos los torneos
    @Override
    public List<TournamentResponseDto> getAllTournaments() {
        return tournamentRepository.findAll().stream()
                .map(tournamentMapper::toDto)
                .collect(Collectors.toList());
    }

    //Obtener un torneo por ID
    @Override
    public TournamentDto getTournamentById(UUID id) {
        Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(() -> new TournamentNotFoundException(id));

        TournamentDto dto = new TournamentDto();
        dto.setId(tournament.getId());
        dto.setName(tournament.getName());
        dto.setMaxPlayers(tournament.getMaxPlayers());
        dto.setStatus(tournament.getStatus());
        dto.setPlayers(tournament.getPlayers());

        return dto;
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
