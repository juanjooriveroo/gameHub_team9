package com.gamehub.service;

import com.gamehub.dto.RankingDto;

import java.util.UUID;

public interface RankingService {

    RankingDto getRanking(UUID id);
}
