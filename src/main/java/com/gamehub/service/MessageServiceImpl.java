package com.gamehub.service;

import com.gamehub.dto.MessageDto;
import com.gamehub.mapper.MessageMapper;
import com.gamehub.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService{

    private final MessageRepository messageRepository;

    private final MessageMapper messageMapper;

    @Override
    public List<MessageDto> findByTournamentIdMessages(UUID tournamentId) {
        return messageRepository.findByTournamentId(tournamentId)
                .stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }
}
