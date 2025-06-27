package com.gamehub.service;

import com.gamehub.dto.message.MessageCreatedDto;
import com.gamehub.dto.message.MessageDto;
import com.gamehub.mapper.MessageMapper;
import com.gamehub.model.Message;
import com.gamehub.model.User;
import com.gamehub.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private MessageMapper messageMapper;


    @Mock
    private UserService userService;

    @InjectMocks
    private MessageServiceImpl messageService;

    private UUID tournamentId;
    private MessageCreatedDto createdDto;
    private Message message;
    private MessageDto messageDto;
    private User user;

    @BeforeEach
    void setUp() {
        tournamentId = UUID.randomUUID();

        createdDto = new MessageCreatedDto();
        createdDto.setContent("¡Hola torneo!");

        message = new Message();
        message.setId(UUID.randomUUID());
        message.setContent("¡Hola torneo!");

        messageDto = new MessageDto();
        messageDto.setContent("¡Hola torneo!");
        messageDto.setSender("Jugador1");

        user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername("Jugador1");
    }

    @Test
    void findByTournamentIdMessages_ValidId_ReturnsDtoList() {
        when(messageRepository.findByTournamentId(tournamentId)).thenReturn(List.of(message));
        when(messageMapper.toDto(message)).thenReturn(messageDto);

        List<MessageDto> result = messageService.findByTournamentIdMessages(tournamentId);

        assertEquals(1, result.size());
        assertEquals("¡Hola torneo!", result.get(0).getContent());
        verify(messageRepository).findByTournamentId(tournamentId);
        verify(messageMapper).toDto(message);
    }

    @Test
    void sendTournamentMessage_ValidInput_SavesAndReturnsDto() {
        when(userService.getCurrentUserEntity()).thenReturn(user);
        when(messageMapper.toEntity(createdDto, user, tournamentId)).thenReturn(message);
        when(messageRepository.save(message)).thenReturn(message);
        when(messageMapper.toDto(message)).thenReturn(messageDto);

        MessageDto result = messageService.sendTournamentMessage(tournamentId, createdDto);

        assertNotNull(result);
        assertEquals("¡Hola torneo!", result.getContent());
        assertEquals("Jugador1", result.getSender());

        verify(userService).getCurrentUserEntity();
        verify(messageMapper).toEntity(createdDto, user, tournamentId);
        verify(messageRepository).save(message);
        verify(messageMapper).toDto(message);
    }
}
