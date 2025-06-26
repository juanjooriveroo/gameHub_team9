package com.gamehub.mapper;

import com.gamehub.dto.RankingDto;
import com.gamehub.dto.UserPublicDto;
import com.gamehub.model.Tournament;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RankingMapper {

    private final UserMapper userMapper;

    public RankingDto toRanking(Tournament tournament) {
        List<UserPublicDto> players = new ArrayList<>();
        tournament.getPlayers().forEach(player -> {
            players.add(userMapper.userPublicDto(player));
        });

        players.sort(Comparator.comparing(UserPublicDto::getPoints).reversed());

        return new RankingDto(players);
    }
}
