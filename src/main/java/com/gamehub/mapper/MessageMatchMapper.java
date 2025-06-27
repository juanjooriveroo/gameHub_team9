package com.gamehub.mapper;

import com.gamehub.dto.MessageMatchDto;
import com.gamehub.dto.MessageMatchRequestDto;
import com.gamehub.model.Match;
import com.gamehub.model.Message;
import com.gamehub.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MessageMatchMapper {

    public MessageMatchDto toDto(Message message){
        return new MessageMatchDto(
                message.getContent(),
                message.getSender(),
                message.getTimestamp());
    }

    public Message toEntity(User user, Match match, MessageMatchRequestDto messageRequestDto) {
        Message message = new Message();
        message.setId(UUID.randomUUID());
        message.setContent(messageRequestDto.getContent());
        message.setSender(user);
        message.setTimestamp(LocalDateTime.now());
        message.setMatch(match);
        message.setTournament(match.getTournament());
        return message;
    }
}
