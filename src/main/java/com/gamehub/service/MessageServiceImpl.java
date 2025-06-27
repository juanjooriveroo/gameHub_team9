package com.gamehub.service;

import com.gamehub.dto.message.MessageCreatedDto;
import com.gamehub.dto.message.MessageDto;
import com.gamehub.exception.TournamentNotFoundException;
import com.gamehub.exception.UnauthorizedException;
import com.gamehub.mapper.MessageMapper;
import com.gamehub.model.Message;
import com.gamehub.model.Tournament;
import com.gamehub.model.User;
import com.gamehub.repository.MessageRepository;
import com.gamehub.repository.TournamentRepository;
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
    private final TournamentRepository tournamentRepository;

    @Override
    public List<MessageDto> findByTournamentIdMessages(UUID tournamentId) {
        return messageRepository.findByTournamentId(tournamentId)
                .stream()
                .map(messageMapper::toDto)
                .collect(Collectors.toList());
    }

    public MessageDto sendTournamentMessage(UUID tournamentId, MessageCreatedDto messageDto) {
        User user = userService.getCurrentUserEntity();

        Message message = messageMapper.toEntity(messageDto, user, tournamentId);

        Message savedMessage = messageRepository.save(message);

        return messageMapper.toDto(savedMessage);

    }
}
