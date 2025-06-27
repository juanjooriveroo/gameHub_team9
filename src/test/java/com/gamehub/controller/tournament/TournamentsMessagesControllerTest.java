package com.gamehub.controller.tournament;

import com.gamehub.dto.message.MessageCreatedDto;
import com.gamehub.dto.message.MessageDto;
import com.gamehub.exception.TournamentNotFoundException;
import com.gamehub.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TournamentsMessagesControllerTest {

    @Mock
    private MessageService messageService;

    @InjectMocks
    private TournamentsMessagesController controller;

    private UUID tournamentId;
    private MessageCreatedDto messageCreatedDto;
    private MessageDto messageDto;

    @BeforeEach
    void setUp() {
        tournamentId = UUID.randomUUID();

        messageCreatedDto = new MessageCreatedDto();
        messageCreatedDto.setContent("¡Hola torneo!");

        messageDto = new MessageDto();
        messageDto.setContent("¡Hola torneo!");
        messageDto.setSender("Jugador1");
    }

    @Test
    void listTournamentMessages_ValidRequest_ReturnsList() {
        when(messageService.findByTournamentIdMessages(tournamentId)).thenReturn(List.of(messageDto));

        ResponseEntity<List<MessageDto>> response = controller.listTournamentMessages(tournamentId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("¡Hola torneo!", response.getBody().get(0).getContent());

        verify(messageService).findByTournamentIdMessages(tournamentId);
    }

    @Test
    void listTournamentMessages_TournamentNotFound_ThrowsException() {
        when(messageService.findByTournamentIdMessages(tournamentId))
                .thenThrow(new TournamentNotFoundException("No encontrado"));

        assertThrows(TournamentNotFoundException.class, () ->
                controller.listTournamentMessages(tournamentId));

        verify(messageService).findByTournamentIdMessages(tournamentId);
    }

    @Test
    void sendMessageToTournament_ValidRequest_ReturnsOk() {
        when(messageService.sendTournamentMessage(tournamentId, messageCreatedDto))
                .thenReturn(messageDto);

        ResponseEntity<?> response = controller.sendMessageToTournament(tournamentId, messageCreatedDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(messageDto, response.getBody());

        verify(messageService).sendTournamentMessage(tournamentId, messageCreatedDto);
    }
}
