package com.gamehub.dto.tournament;

import lombok.Data;

@Data
public class TournamentRequestDto {
    private String name;
    private int maxPlayers;
}
