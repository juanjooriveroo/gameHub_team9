package com.gamehub.mapper;

import com.gamehub.dto.UserDto;
import com.gamehub.dto.UserPublicDto;
import com.gamehub.dto.tournament.TournamentDto;
import com.gamehub.dto.tournament.TournamentRequestDto;
import com.gamehub.dto.tournament.TournamentResponseDto;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TournamentMapper {

    private final UserMapper userMapper;

    public TournamentResponseDto toDto (Tournament tournament){
        TournamentResponseDto dto = new TournamentResponseDto();
        dto.setId(tournament.getId());
        dto.setName(tournament.getName());
        dto.setMaxPlayers(tournament.getMaxPlayers());
        dto.setStatus(tournament.getStatus());

        return dto;
    }

    public Tournament toEntity (TournamentRequestDto dto){
        Tournament tournament = new Tournament();
        tournament.setId(UUID.randomUUID());
        tournament.setName(dto.getName());
        tournament.setMaxPlayers(dto.getMaxPlayers());
        tournament.setStatus(dto.getStatus());

        return tournament;
    }


}
