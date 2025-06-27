package com.gamehub.service;

import com.gamehub.dto.MessageMatchDto;
import com.gamehub.dto.MessageMatchListDto;
import com.gamehub.dto.MessageMatchRequestDto;
import com.gamehub.exception.AnyMessageMatchException;
import com.gamehub.exception.MatchNotFoundException;
import com.gamehub.mapper.MessageMatchMapper;
import com.gamehub.model.Match;
import com.gamehub.model.Message;
import com.gamehub.model.User;
import com.gamehub.repository.MatchRepository;
import com.gamehub.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceMatchImplTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private MessageMatchMapper messageMatchMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private MessageServiceMatchImpl messageServiceMatch;

    private Match match;
    private Message message;
    private MessageMatchRequestDto messageRequestDto;
    private User sender;
    private UUID matchId;

    @BeforeEach
    void setUp() {
        matchId = UUID.randomUUID();

        match = new Match();
        match.setId(matchId);

        sender = new User();
        sender.setId(UUID.randomUUID());
        sender.setUsername("player1");

        messageRequestDto = new MessageMatchRequestDto();
        messageRequestDto.setContent("Test Message");

        message = new Message();
        message.setId(UUID.randomUUID());
        message.setContent(messageRequestDto.getContent());
        message.setSender(sender);
        message.setTimestamp(LocalDateTime.now());
        message.setMatch(match);
    }

    @Test
    void sendMessageToMatch_ValidRequest_ReturnsTrue() {
        when(userService.getCurrentUserEntity()).thenReturn(sender);
        when(matchRepository.getMatchById(matchId)).thenReturn(match);
        when(messageMatchMapper.toEntity(sender, match, messageRequestDto)).thenReturn(message);
        when(messageRepository.save(message)).thenReturn(message);

        boolean result = messageServiceMatch.sendMessageToMatch(matchId, messageRequestDto);

        assertTrue(result);
        verify(messageRepository).save(message);
    }

    @Test
    void sendMessageToMatch_MatchNotFound_ThrowsException() {
        when(userService.getCurrentUserEntity()).thenReturn(sender);
        when(matchRepository.getMatchById(matchId)).thenThrow(MatchNotFoundException.class);

        assertThrows(MatchNotFoundException.class, () -> {
            messageServiceMatch.sendMessageToMatch(matchId, messageRequestDto);
        });
    }

    @Test
    void getListMatchMessages_ValidRequest_ReturnsMessages() {
        List<Message> messages = new ArrayList<>(List.of(message));
        when(matchRepository.getMatchById(matchId)).thenReturn(match);
        when(messageRepository.findAllByMatch_Id(matchId)).thenReturn(messages);
        when(messageMatchMapper.toDto(any())).thenReturn(new MessageMatchDto(message.getContent(), sender, message.getTimestamp()));

        MessageMatchListDto result = messageServiceMatch.getListMatchMessages(matchId);

        assertNotNull(result);
        assertEquals(1, result.getMessages().size());
        verify(messageRepository).findAllByMatch_Id(matchId);
    }

    @Test
    void getListMatchMessages_NoMessages_ThrowsException() {
        when(matchRepository.getMatchById(matchId)).thenReturn(match);
        when(messageRepository.findAllByMatch_Id(matchId)).thenReturn(new ArrayList<>());

        assertThrows(AnyMessageMatchException.class, () -> {
            messageServiceMatch.getListMatchMessages(matchId);
        });
    }
}