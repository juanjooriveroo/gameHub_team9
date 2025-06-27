package com.gamehub.service;

import com.gamehub.dto.message.MessageDto;
import com.gamehub.exception.TournamentNotFoundException;
import com.gamehub.exception.UnauthorizedException;
import com.gamehub.mapper.MessageMapper;
import com.gamehub.model.Message;
import com.gamehub.model.Tournament;
import com.gamehub.model.User;
import com.gamehub.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;

    private final MessageMapper messageMapper;

    private final TournamentService tournamentService;

    private final UserService userService;

    private final MatchService matchService;

    @Override
    public List<MessageDto> findByTournamentIdMessages(UUID tournamentId) {
        return messageRepository.findByTournamentId(tournamentId)
                .stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }

    public MessageDto sendTournamentMessage(UUID tournamentId, MessageDto messageDto, UUID senderId) {

        Tournament tournament = tournamentService.findById(tournamentId)
                .orElseThrow(() -> new TournamentNotFoundException("Tournament not found"));

        User sender = userService.findById(senderId)
                .orElseThrow(() -> new UnauthorizedException("User not found"));

        Message message = messageMapper.toEntity(messageDto, sender, tournament);

        Message savedMessage = messageRepository.save(message);

        return messageMapper.toDto(savedMessage);

    }
}
