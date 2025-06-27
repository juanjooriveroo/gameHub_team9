package com.gamehub.service;

import com.gamehub.dto.match.MatchResponseDto;
import com.gamehub.dto.match.MatchResultUpdateDto;
import com.gamehub.exception.MatchNotFoundException;
import com.gamehub.exception.ResultInvalidException;
import com.gamehub.exception.TournamentNotFoundException;
import com.gamehub.model.Match;
import com.gamehub.model.Result;
import com.gamehub.model.Tournament;
import com.gamehub.model.User;
import com.gamehub.repository.MatchRepository;
import com.gamehub.repository.TournamentRepository;
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
class MatchServiceImplTest {

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private TournamentRepository tournamentRepository;

    @InjectMocks
    private MatchServiceImpl matchService;

    private Tournament tournament;
    private List<User> players;
    private UUID tournamentId;
    private UUID matchId;
    private Match match;

    @BeforeEach
    void setUp() {
        tournamentId = UUID.randomUUID();
        matchId = UUID.randomUUID();

        // Crear jugadores simulados
        User player1 = new User();
        player1.setId(UUID.randomUUID());
        player1.setUsername("Player1");

        User player2 = new User();
        player2.setId(UUID.randomUUID());
        player2.setUsername("Player2");

        // Crear torneo simulado
        tournament = new Tournament();
        tournament.setId(tournamentId);
        tournament.setPlayers(List.of(player1, player2));

        // Crear partida simulada y asignar torneo
        match = new Match();
        match.setId(matchId);
        match.setPlayer1(player1);
        match.setPlayer2(player2);
        match.setResult(Result.PENDING);
        match.setTournament(tournament); // Asignar torneo
    }

    @Test
    void generateMatches_ValidTournamentId_ShouldGenerateMatches() {
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));
        when(matchRepository.findAllByTournament_Id(tournamentId)).thenReturn(new ArrayList<>());

        matchService.generateMatches(tournamentId);

        verify(matchRepository, times(1)).save(any(Match.class));
        verify(tournamentRepository, times(1)).findById(tournamentId);
    }

    @Test
    void generateMatches_TournamentNotFound_ShouldThrowException() {
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.empty());

        assertThrows(TournamentNotFoundException.class, () -> {
            matchService.generateMatches(tournamentId);
        });

        verify(tournamentRepository).findById(tournamentId);
        verifyNoInteractions(matchRepository);
    }

    @Test
    void getMatchById_ValidId_ShouldReturnMatchResponseDto() {
        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));

        MatchResponseDto response = matchService.getMatchById(matchId);

        assertNotNull(response);
        assertEquals(matchId, response.getId());
        assertEquals(match.getPlayer1().getId(), response.getPlayerOneId());
        assertEquals(match.getPlayer2().getId(), response.getPlayerTwoId());
        assertEquals(match.getResult(), response.getResult());
        assertEquals(tournamentId, response.getTournamentId()); // Validación del torneo

        verify(matchRepository).findById(matchId);
    }

    @Test
    void getMatchById_InvalidId_ShouldThrowException() {
        when(matchRepository.findById(matchId)).thenReturn(Optional.empty());

        assertThrows(MatchNotFoundException.class, () -> {
            matchService.getMatchById(matchId);
        });

        verify(matchRepository).findById(matchId);
    }

    @Test
    void updateMatchResult_ValidInput_ShouldUpdateResult() {
        MatchResultUpdateDto updateDto = new MatchResultUpdateDto();
        updateDto.setResult("PLAYER1_WIN");

        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));

        matchService.updateMatchResult(matchId, updateDto);

        assertEquals(Result.PLAYER1_WIN, match.getResult());
        verify(matchRepository).findById(matchId);
        verify(matchRepository).save(match);
    }

    @Test
    void updateMatchResult_InvalidInput_ShouldThrowResultInvalidException() {
        MatchResultUpdateDto updateDto = new MatchResultUpdateDto();
        updateDto.setResult("INVALID_RESULT");

        when(matchRepository.findById(matchId)).thenReturn(Optional.of(match));

        assertThrows(ResultInvalidException.class, () -> {
            matchService.updateMatchResult(matchId, updateDto);
        });

        verify(matchRepository).findById(matchId);
        verify(matchRepository, never()).save(any(Match.class));
    }

    @Test
    void updateMatchResult_MatchNotFound_ShouldThrowMatchNotFoundException() {
        MatchResultUpdateDto updateDto = new MatchResultUpdateDto();
        updateDto.setResult("PLAYER1_WIN");

        when(matchRepository.findById(matchId)).thenReturn(Optional.empty());

        assertThrows(MatchNotFoundException.class, () -> {
            matchService.updateMatchResult(matchId, updateDto);
        });

        verify(matchRepository).findById(matchId);
        verify(matchRepository, never()).save(any(Match.class));
    }
}