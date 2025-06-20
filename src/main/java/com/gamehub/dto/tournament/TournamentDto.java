package com.gamehub.dto.tournament;

import com.gamehub.model.Status;
import com.gamehub.model.User;
import lombok.Data;

import java.util.List;

@Data
public class TournamentDto {
    private String id;
    private String name;
    private int maxPlayers;
    private Status status;
    private List<User> players;
}
