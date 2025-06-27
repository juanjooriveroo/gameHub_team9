package com.gamehub.mapper;

import com.gamehub.dto.message.MessageCreatedDto;
import com.gamehub.dto.message.MessageDto;
import com.gamehub.exception.TournamentNotFoundException;
import com.gamehub.exception.UserNotFoundException;
import com.gamehub.model.Match;
import com.gamehub.model.Message;
import com.gamehub.model.Tournament;
import com.gamehub.model.User;
import com.gamehub.repository.TournamentRepository;
import com.gamehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class MessageMapper {

    private final UserRepository userRepository;
    private final TournamentRepository tournamentRepository;

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

    public Message toEntity(MessageCreatedDto messageDto, User user, UUID tournamentId) {
        if (messageDto == null) {
            return null;
        }

        Tournament tournament = tournamentRepository.findById(tournamentId).orElse(null);

        if (tournament == null) {
            throw new TournamentNotFoundException("Tournament not found");
        }

        Message message = new Message();
        message.setId(UUID.randomUUID());
        message.setContent(messageDto.getContent());
        message.setTimestamp(LocalDateTime.now());
        message.setSender(user);
        message.setTournament(tournament);

        return message;
    }
}
