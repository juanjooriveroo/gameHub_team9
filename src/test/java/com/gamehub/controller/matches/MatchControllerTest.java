package com.gamehub.controller.matches;

import com.gamehub.dto.match.MatchResponseDto;
import com.gamehub.dto.match.MatchResultUpdateDto;
import com.gamehub.service.MatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchControllerTest {

    @Mock
    private MatchService matchService;

    @InjectMocks
    private MatchController matchController;

    private UUID matchId;
    private MatchResponseDto responseDto;

    @BeforeEach
    void setUp() {
        matchId = UUID.randomUUID();

        // Crear DTO de respuesta simulado
        responseDto = new MatchResponseDto();
        responseDto.setId(matchId);
        responseDto.setResult(com.gamehub.model.Result.PLAYER1_WIN);
    }

    @Test
    void getMatch_ValidId_ShouldReturnMatchResponseDto() {
        when(matchService.getMatchById(matchId)).thenReturn(responseDto);

        ResponseEntity<MatchResponseDto> response = matchController.getMatch(matchId);

        assertEquals(responseDto, response.getBody());
        assertEquals(200, response.getStatusCodeValue());
        verify(matchService).getMatchById(matchId);
    }

    @Test
    void updateResult_ValidInput_ShouldUpdateMatch() {
        MatchResultUpdateDto updateDto = new MatchResultUpdateDto();
        updateDto.setResult("PLAYER1_WIN");

        doNothing().when(matchService).updateMatchResult(matchId, updateDto);

        ResponseEntity<Void> response = matchController.updateResult(matchId, updateDto);

        assertEquals(200, response.getStatusCodeValue());
        verify(matchService).updateMatchResult(matchId, updateDto);
    }

    @Test
    void generateMatches_ValidTournamentId_ShouldCallService() {
        UUID tournamentId = UUID.randomUUID();

        doNothing().when(matchService).generateMatches(tournamentId);

        ResponseEntity<Void> response = matchController.generateMatches(tournamentId);

        assertEquals(200, response.getStatusCodeValue());
        verify(matchService).generateMatches(tournamentId);
    }
}