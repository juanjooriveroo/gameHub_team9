package com.gamehub.dto.match;

import java.util.UUID;
import lombok.Data;

@Data
public class MatchRequestDto {
    private UUID playerOneId;
    private UUID playerTwoId;
    private UUID tournamentId;
}
