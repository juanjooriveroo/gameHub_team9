package com.gamehub.service;

import com.gamehub.dto.message.MessageDto;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    List<MessageDto> findByTournamentIdMessages(UUID tournamentId);

    MessageDto sendTournamentMessage(UUID tournamentId, MessageDto messageDto, UUID senderId);
}
