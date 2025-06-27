package com.gamehub.service;

import com.gamehub.dto.MessageMatchListDto;
import com.gamehub.dto.MessageMatchRequestDto;

import java.util.UUID;

public interface MessageMatchService {
    public boolean sendMessageToMatch(UUID id, MessageMatchRequestDto messageRequestDto);
    public MessageMatchListDto getListMatchMessages(UUID id);
}
