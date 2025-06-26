package com.gamehub.service;

import com.gamehub.dto.tournament.TournamentDto;
import com.gamehub.dto.tournament.TournamentRequestDto;
import com.gamehub.dto.tournament.TournamentResponseDto;
import com.gamehub.exception.*;
import com.gamehub.mapper.TournamentMapper;
import com.gamehub.model.Role;
import com.gamehub.model.Tournament;
import com.gamehub.model.User;
import com.gamehub.repository.TournamentRepository;
import com.gamehub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;

    private final TournamentMapper tournamentMapper;

    private final UserRepository userRepository;

    //Creación de un torneo
    @Transactional
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
                .orElseThrow(() -> new TournamentNotFoundException("Tournament not found"));

        TournamentDto dto = new TournamentDto();
        dto.setId(tournament.getId().toString());
        dto.setName(tournament.getName());
        dto.setMaxPlayers(tournament.getMaxPlayers());
        dto.setStatus(tournament.getStatus());
        dto.setPlayers(tournament.getPlayers());

        return dto;
    }

    //Unirse a un torneo
    @Override
    public void joinTournament(UUID tournamentId, UUID userId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(()-> new TournamentNotFoundException("Tournament not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        if (!user.getRole().equals(Role.PLAYER)){
            throw new UnauthorizedException("Only players can join tournaments");
        }

        if(tournament.getPlayers().size() >= tournament.getMaxPlayers()){
            throw new TournamentFullException("Tournament is full");
        }

        if(tournament.getPlayers().contains(user)){
            throw new UserAlreadyJoinedException("User already joined the tournament");
        }

        tournament.getPlayers().add(user);
        tournamentRepository.save(tournament);
    }

}
