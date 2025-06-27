package com.gamehub.mapper;

import com.gamehub.dto.message.MessageDto;
import com.gamehub.model.Match;
import com.gamehub.model.Message;
import com.gamehub.model.Tournament;
import com.gamehub.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MessageMapper {

    private final UserMapper userMapper;

    public MessageDto toDto(Message message) {
        if (message == null) {
            return null;
        }

        MessageDto dto = new MessageDto();
        dto.setContent(message.getContent());
        dto.setSender(message.getSender().getUsername());
        dto.setTimestamp(message.getTimestamp());

        return dto;
    }

    public Message toEntity(MessageDto messageDto, User sender, Tournament tournament) {
        if (messageDto == null) {
            return null;
        }

        Message message = new Message();
        message.setId(UUID.randomUUID());
        message.setContent(messageDto.getContent());
        message.setTimestamp(LocalDateTime.now());
        message.setSender(sender);
        message.setTournament(tournament);

        return message;
    }
}
