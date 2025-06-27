package com.gamehub.mapper;

import com.gamehub.dto.MessageDto;
import com.gamehub.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
}
