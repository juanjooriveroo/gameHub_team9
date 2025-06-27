package com.gamehub.controller;

import com.gamehub.dto.tournament.TournamentDto;
import com.gamehub.dto.tournament.TournamentRequestDto;
import com.gamehub.dto.tournament.TournamentResponseDto;
import com.gamehub.exception.TournamentNotFoundException;
import com.gamehub.model.Role;
import com.gamehub.model.Status;
import com.gamehub.model.User;
import com.gamehub.service.TournamentService;
import com.gamehub.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TournamentsControllerTest {

    @Mock
    private TournamentService tournamentService;

    @Mock
    private UserService userService;

    @InjectMocks
    private TournamentsController tournamentsController;

    private TournamentRequestDto tournamentRequestDto;
    private TournamentResponseDto tournamentResponseDto;
    private TournamentDto tournamentDto;
    private UUID tournamentId;

    @BeforeEach
    void setUp() {
        tournamentId = UUID.randomUUID();

        tournamentRequestDto = new TournamentRequestDto();
        tournamentRequestDto.setName("Tournament 1");
        tournamentRequestDto.setMaxPlayers(16);
        tournamentRequestDto.setStatus(Status.CREATED);

        tournamentResponseDto = new TournamentResponseDto();
        tournamentResponseDto.setId(tournamentId);
        tournamentResponseDto.setName("Tournament 1");
        tournamentResponseDto.setMaxPlayers(16);
        tournamentResponseDto.setStatus(Status.CREATED);

        tournamentDto = new TournamentDto();
        tournamentDto.setId(tournamentId.toString());
        tournamentDto.setName("Tournament 1");
    }

    @Test
    void createTournament_ValidRequest_ReturnsCreated() {
        when(tournamentService.createTournament(tournamentRequestDto)).thenReturn(tournamentResponseDto);

        ResponseEntity<TournamentResponseDto> response = tournamentsController.createTournament(tournamentRequestDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(tournamentResponseDto, response.getBody());
        verify(tournamentService).createTournament(tournamentRequestDto);
    }

    @Test
    void listTournaments_ReturnsTournamentList() {
        when(tournamentService.getAllTournaments()).thenReturn(Collections.singletonList(tournamentResponseDto));

        ResponseEntity<List<TournamentResponseDto>> response = tournamentsController.listTournaments();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(tournamentService).getAllTournaments();
    }

    @Test
    void getTournamentById_ValidId_ReturnsTournamentDto() {
        when(tournamentService.getTournamentById(tournamentId)).thenReturn(tournamentDto);

        ResponseEntity<TournamentDto> response = tournamentsController.getTournamentById(tournamentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tournamentDto, response.getBody());
        verify(tournamentService).getTournamentById(tournamentId);
    }

    @Test
    void getTournamentById_InvalidId_ThrowsException() {
        when(tournamentService.getTournamentById(tournamentId))
                .thenThrow(new TournamentNotFoundException("Tournament not found"));

        assertThrows(TournamentNotFoundException.class, () -> {
            tournamentsController.getTournamentById(tournamentId);
        });

        verify(tournamentService).getTournamentById(tournamentId);
    }

    @Test
    void joinTournament_ValidIds_ReturnsSuccessMessage() {
        UUID userId = UUID.randomUUID();

        User currentUser = new User();
        currentUser.setId(userId);
        currentUser.setUsername("testuser");
        currentUser.setRole(Role.PLAYER);

        when(userService.getCurrentUserEntity()).thenReturn(currentUser);
        doNothing().when(tournamentService).joinTournament(tournamentId, userId);

        ResponseEntity<?> response = tournamentsController.joinTournament(tournamentId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Te has unido al torneo correctamente.", response.getBody());
        verify(tournamentService).joinTournament(tournamentId, userId);
    }
}