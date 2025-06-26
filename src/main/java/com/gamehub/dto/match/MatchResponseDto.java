package com.gamehub.dto.match;

import java.util.UUID;

import com.gamehub.model.Result;

import lombok.Data;

@Data
public class MatchResponseDto {
    private UUID id;
    private UUID playerOneId;
    private UUID playerTwoId;
    private Result result;
    private UUID tournamentId;
}
