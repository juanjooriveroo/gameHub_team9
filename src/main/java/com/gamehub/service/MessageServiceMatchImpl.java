package com.gamehub.service;

import com.gamehub.dto.MessageMatchDto;
import com.gamehub.dto.MessageMatchListDto;
import com.gamehub.dto.MessageMatchRequestDto;
import com.gamehub.exception.AnyMessageMatchException;
import com.gamehub.exception.MatchNotFoundException;
import com.gamehub.mapper.MessageMatchMapper;
import com.gamehub.model.Match;
import com.gamehub.model.Message;
import com.gamehub.model.User;
import com.gamehub.repository.MatchRepository;
import com.gamehub.repository.MessageRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageServiceMatchImpl implements MessageMatchService{

    private final MessageRepository messageRepository;
    private final MessageMatchMapper messageMatchMapper;
    private final MatchRepository matchRepository;
    private final UserService userService;

    @Transactional
    @Override
    public boolean sendMessageToMatch(UUID id, MessageMatchRequestDto messageRequestDto) {
        User user = userService.getCurrentUserEntity();
        Match match = matchRepository.getMatchById(id);

        Message message = messageMatchMapper.toEntity(user, match, messageRequestDto);
        Message messageSaved = messageRepository.save(message);
        return messageSaved.getId() == message.getId() && messageSaved.getSender() == message.getSender();
    }

    @Transactional
    @Override
    public MessageMatchListDto getListMatchMessages(UUID id) {
        if (matchRepository.getMatchById(id) == null) {
            throw new MatchNotFoundException("No hay partido con id: " + id);
        }

        List<Message> messageList = messageRepository.findAllByMatch_Id(id);

        if(messageList.isEmpty()){
            throw new AnyMessageMatchException("No hay mensajes para el partido con id: " + id);
        }

        List<MessageMatchDto> messageMatchList = new ArrayList<>();
        messageList.forEach(message -> {
            messageMatchList.add(messageMatchMapper.toDto(message));
        });

        messageList.sort(Comparator.comparing(Message::getTimestamp).reversed());
        return new MessageMatchListDto(messageMatchList);
    }
}
