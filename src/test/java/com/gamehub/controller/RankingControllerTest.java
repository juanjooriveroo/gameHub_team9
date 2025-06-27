package com.gamehub.controller;

import com.gamehub.dto.RankingDto;
import com.gamehub.dto.UserPublicDto;
import com.gamehub.exception.TournamentNotFoundException;
import com.gamehub.service.RankingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RankingControllerTest {

    @Mock
    private RankingService rankingService;

    @InjectMocks
    private RankingController rankingController;

    private UUID tournamentId;
    private RankingDto rankingDto;

    @BeforeEach
    void setUp() {
        tournamentId = UUID.randomUUID();

        List<UserPublicDto> players = new ArrayList<>();
        UserPublicDto player1 = new UserPublicDto();
        player1.setUsername("player1");
        player1.setRank("Gold");
        player1.setPoints(30);

        UserPublicDto player2 = new UserPublicDto();
        player2.setUsername("player2");
        player2.setRank("Silver");
        player2.setPoints(20);

        players.add(player1);
        players.add(player2);

        rankingDto = new RankingDto(players);
    }

    @Test
    void rankingTournament_ValidId_ReturnsRanking() {
        when(rankingService.getRanking(tournamentId)).thenReturn(rankingDto);

        ResponseEntity<?> response = rankingController.rankingTournament(tournamentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(rankingDto, response.getBody());
        verify(rankingService).getRanking(tournamentId);
    }

    @Test
    void rankingTournament_InvalidId_ThrowsTournamentNotFoundException() {
        when(rankingService.getRanking(tournamentId))
                .thenThrow(new TournamentNotFoundException("Tournament not found."));

        assertThrows(TournamentNotFoundException.class, () -> {
            rankingController.rankingTournament(tournamentId);
        });

        verify(rankingService).getRanking(tournamentId);
    }
}