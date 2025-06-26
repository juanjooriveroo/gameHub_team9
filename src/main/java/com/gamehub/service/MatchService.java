package com.gamehub.service;

import com.gamehub.dto.match.*;
import java.util.UUID;

public interface MatchService {
    void generateMatches(UUID tournamentId);
    MatchResponseDto getMatchById(UUID id);
    void updateMatchResult(UUID id, MatchResultUpdateDto dto);
}
