package com.gamehub.service;

import com.gamehub.dto.tournament.TournamentDto;
import com.gamehub.dto.tournament.TournamentRequestDto;
import com.gamehub.dto.tournament.TournamentResponseDto;
import com.gamehub.exception.TournamentFullException;
import com.gamehub.exception.TournamentNotFoundException;
import com.gamehub.exception.UserAlreadyJoinedException;
import com.gamehub.mapper.TournamentMapper;
import com.gamehub.model.Role;
import com.gamehub.model.Status;
import com.gamehub.model.Tournament;
import com.gamehub.model.User;
import com.gamehub.repository.TournamentRepository;
import com.gamehub.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TournamentServiceImplTest {

    @Mock
    private TournamentRepository tournamentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TournamentMapper tournamentMapper;

    @InjectMocks
    private TournamentServiceImpl tournamentService;

    private Tournament tournament;
    private TournamentRequestDto tournamentRequestDto;
    private TournamentResponseDto tournamentResponseDto;
    private TournamentDto tournamentDto;
    private User user;
    private UUID tournamentId;
    private UUID userId;

    @BeforeEach
    void setUp() {
        tournamentId = UUID.randomUUID();
        userId = UUID.randomUUID();

        tournament = new Tournament();
        tournament.setId(tournamentId);
        tournament.setName("Tournament 1");
        tournament.setMaxPlayers(2);
        tournament.setStatus(Status.CREATED);
        tournament.setPlayers(new ArrayList<>());

        tournamentRequestDto = new TournamentRequestDto();
        tournamentRequestDto.setName("Tournament 1");
        tournamentRequestDto.setMaxPlayers(2);
        tournamentRequestDto.setStatus(Status.CREATED);

        tournamentResponseDto = new TournamentResponseDto();
        tournamentResponseDto.setId(tournamentId);
        tournamentResponseDto.setName("Tournament 1");
        tournamentResponseDto.setMaxPlayers(2);
        tournamentResponseDto.setStatus(Status.CREATED);

        tournamentDto = new TournamentDto();
        tournamentDto.setId(tournamentId.toString());
        tournamentDto.setName("Tournament 1");
        tournamentDto.setMaxPlayers(2);
        tournamentDto.setStatus(Status.CREATED);

        user = new User();
        user.setId(userId);
        user.setUsername("player1");
        user.setRole(Role.PLAYER);
    }

    @Test
    void createTournament_ValidRequest_SavesTournament() {
        when(tournamentMapper.toEntity(tournamentRequestDto)).thenReturn(tournament);
        when(tournamentRepository.save(tournament)).thenReturn(tournament);
        when(tournamentMapper.toDto(tournament)).thenReturn(tournamentResponseDto);

        TournamentResponseDto result = tournamentService.createTournament(tournamentRequestDto);

        assertNotNull(result);
        assertEquals(tournamentResponseDto, result);
        verify(tournamentRepository).save(tournament);
    }

    @Test
    void getAllTournaments_ReturnsTournamentList() {
        when(tournamentRepository.findAll()).thenReturn(List.of(tournament));
        when(tournamentMapper.toDto(tournament)).thenReturn(tournamentResponseDto);

        List<TournamentResponseDto> result = tournamentService.getAllTournaments();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(tournamentResponseDto, result.get(0));
    }

    @Test
    void getTournamentById_ValidId_ReturnsDto() {
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));

        TournamentDto result = tournamentService.getTournamentById(tournamentId);

        assertNotNull(result);
        assertEquals(tournament.getId().toString(), result.getId());
    }

    @Test
    void joinTournament_UserJoinsSuccessfully() {
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        tournamentService.joinTournament(tournamentId, userId);

        assertEquals(1, tournament.getPlayers().size());
        assertTrue(tournament.getPlayers().contains(user));
    }

    @Test
    void joinTournament_TournamentFull_ThrowsException() {
        tournament.getPlayers().add(new User());
        tournament.getPlayers().add(new User());

        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertThrows(TournamentFullException.class, () -> {
            tournamentService.joinTournament(tournamentId, userId);
        });

        verify(tournamentRepository).findById(tournamentId);
        verify(userRepository).findById(userId);
    }

    @Test
    void joinTournament_UserAlreadyJoined_ThrowsException() {
        tournament.getPlayers().add(user);

        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyJoinedException.class, () -> {
            tournamentService.joinTournament(tournamentId, userId);
        });
    }
}