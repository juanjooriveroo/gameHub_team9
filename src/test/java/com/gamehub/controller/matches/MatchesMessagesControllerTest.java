package com.gamehub.controller.matches;

import com.gamehub.dto.MessageMatchListDto;
import com.gamehub.dto.MessageMatchRequestDto;
import com.gamehub.exception.AnyMessageMatchException;
import com.gamehub.service.MessageMatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchesMessagesControllerTest {

    @Mock
    private MessageMatchService messageMatchService;

    @InjectMocks
    private MatchesMessagesController matchesMessagesController;

    private UUID matchId;
    private MessageMatchRequestDto messageRequestDto;
    private MessageMatchListDto messageMatchListDto;

    @BeforeEach
    void setUp() {
        matchId = UUID.randomUUID();

        messageRequestDto = new MessageMatchRequestDto();
        messageRequestDto.setContent("Test message");

        messageMatchListDto = new MessageMatchListDto();
    }

    @Test
    void listMatchMessages_ValidRequest_ReturnsMessageList() {
        when(messageMatchService.getListMatchMessages(matchId)).thenReturn(messageMatchListDto);

        ResponseEntity<MessageMatchListDto> response = matchesMessagesController.listMatchMessages(matchId);

        assertNotNull(response);
        assertEquals(messageMatchListDto, response.getBody());
        verify(messageMatchService).getListMatchMessages(matchId);
    }

    @Test
    void listMatchMessages_MatchNotFound_ThrowsException() {
        when(messageMatchService.getListMatchMessages(matchId)).thenThrow(AnyMessageMatchException.class);

        assertThrows(AnyMessageMatchException.class, () -> {
            matchesMessagesController.listMatchMessages(matchId);
        });

        verify(messageMatchService).getListMatchMessages(matchId);
    }

    @Test
    void sendMessageToMatch_ValidRequest_ReturnsOk() {
        when(messageMatchService.sendMessageToMatch(matchId, messageRequestDto)).thenReturn(true);

        ResponseEntity<?> response = matchesMessagesController.sendMessageToMatch(matchId, messageRequestDto);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        verify(messageMatchService).sendMessageToMatch(matchId, messageRequestDto);
    }
}