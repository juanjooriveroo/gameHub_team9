package com.gamehub.service;

import com.gamehub.dto.MessageDto;

import java.util.List;
import java.util.UUID;

public interface MessageService {

    List<MessageDto> findByTournamentIdMessages(UUID tournamentId);
}
