package com.gamehub.service;

import com.gamehub.dto.match.*;
import com.gamehub.model.Match;
import com.gamehub.model.Result;
import com.gamehub.model.Tournament;
import com.gamehub.model.User;
import com.gamehub.repository.MatchRepository;
import com.gamehub.repository.TournamentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TournamentRepository tournamentRepository;

    @Transactional
    @Override
    public void generateMatches(UUID tournamentId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));

        List<User> players = tournament.getPlayers();
        Collections.shuffle(players); 

        for (int i = 0; i < players.size() - 1; i += 2) {
            Match match = new Match();
            match.setId(UUID.randomUUID());
            match.setPlayer1(players.get(i));
            match.setPlayer2(players.get(i + 1));
            match.setTournament(tournament);
            match.setRound(1); 

            matchRepository.save(match);
        }
    }

    @Override
    public MatchResponseDto getMatchById(UUID id) {
        Match match = matchRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Partida no encontrada"));

        MatchResponseDto dto = new MatchResponseDto();
        dto.setId(match.getId());
        dto.setPlayerOneId(match.getPlayer1().getId());
        dto.setPlayerTwoId(match.getPlayer2().getId());
        dto.setResult(match.getResult());
        dto.setTournamentId(match.getTournament().getId());

        return dto;
    }


    @Override
    public void updateMatchResult(UUID id, MatchResultUpdateDto dto) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partida no encontrada"));

        try {
            match.setResult(Result.valueOf(dto.getResult()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Valor inválido para el resultado: " + dto.getResult());
        }

        matchRepository.save(match);
    }

}
