package com.gamehub.service;

import com.gamehub.dto.RankingDto;
import com.gamehub.dto.UserPublicDto;
import com.gamehub.exception.TournamentNotFoundException;
import com.gamehub.mapper.RankingMapper;
import com.gamehub.model.Match;
import com.gamehub.model.Result;
import com.gamehub.model.Tournament;
import com.gamehub.model.User;
import com.gamehub.repository.MatchRepository;
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
class RankingServiceImplTest {

    @Mock
    private TournamentRepository tournamentRepository;

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RankingMapper rankingMapper;

    @InjectMocks
    private RankingServiceImpl rankingService;

    private UUID tournamentId;
    private Tournament tournament;
    private User player1, player2;
    private Match match1, match2;
    private List<Match> matches;

    @BeforeEach
    void setUp() {
        // Crear IDs consistentes para los jugadores
        UUID player1Id = UUID.randomUUID();
        UUID player2Id = UUID.randomUUID();

        player1 = new User();
        player1.setId(player1Id);
        player1.setUsername("player1");
        player1.setPoints(0);

        player2 = new User();
        player2.setId(player2Id);
        player2.setUsername("player2");
        player2.setPoints(0);

        tournament = new Tournament();
        tournament.setId(tournamentId);
        tournament.setName("Tournament Example");
        tournament.setPlayers(List.of(player1, player2));

        match1 = new Match();
        match1.setPlayer1(player1);
        match1.setPlayer2(player2);
        match1.setResult(Result.PLAYER1_WIN); // +3 puntos a player1

        match2 = new Match();
        match2.setPlayer1(player1);
        match2.setPlayer2(player2);
        match2.setResult(Result.PLAYER2_WIN); // -1 punto a player1, +3 a player2

        matches = List.of(match1, match2);
    }

    @Test
    void getRanking_ValidId_ReturnsRankingDto() {
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));
        when(matchRepository.findByTournament_Id(tournamentId)).thenReturn(matches);
        when(rankingMapper.toRanking(tournament)).thenReturn(new RankingDto(new ArrayList<>()));

        RankingDto result = rankingService.getRanking(tournamentId);

        assertNotNull(result);
        verify(tournamentRepository, times(2)).findById(tournamentId);
        verify(matchRepository, atLeast(1)).findByTournament_Id(tournamentId);
    }

    @Test
    void getRanking_InvalidId_ThrowsTournamentNotFoundException() {
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.empty());

        assertThrows(TournamentNotFoundException.class, () -> {
            rankingService.getRanking(tournamentId);
        });

        verify(tournamentRepository).findById(tournamentId);
        verifyNoInteractions(matchRepository);
        verifyNoInteractions(rankingMapper);
    }

    @Test
    void getRanking_UpdatePlayerPointsCorrectly() {
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));
        when(matchRepository.findByTournament_Id(tournamentId)).thenReturn(matches);

        // Ejecutar el método principal
        rankingService.getRanking(tournamentId);

        // Verificar los puntos actualizados
        assertEquals(2, player1.getPoints());
        assertEquals(2, player2.getPoints());

        verify(userRepository, times(2)).save(any(User.class));
    }
}