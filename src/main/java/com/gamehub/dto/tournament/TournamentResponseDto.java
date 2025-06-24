package com.gamehub.dto.tournament;

import com.gamehub.model.Status;
import jdk.jshell.Snippet;
import lombok.Data;

import java.util.UUID;

@Data
public class TournamentResponseDto {
    private UUID id;
    private String name;
    private int maxPlayers;
    private Status status;
}
