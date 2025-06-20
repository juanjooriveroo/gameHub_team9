package com.gamehub.dto.tournament;

import com.gamehub.model.Status;
import lombok.Data;

@Data
public class TournamentRequestDto {
    private String name;
    private int maxPlayers;
    private Status status;
}
