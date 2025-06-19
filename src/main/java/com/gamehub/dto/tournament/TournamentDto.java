package com.gamehub.dto.tournament;

import lombok.Data;

@Data
public class TournamentDto {
    private String id;
    private String name;
    private int maxPlayers;
    private Status status;
    private List<User> players;
}
