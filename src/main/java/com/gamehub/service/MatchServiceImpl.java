package com.gamehub.service;

import com.gamehub.dto.match.*;
import com.gamehub.exception.MatchNotFoundException;
import com.gamehub.exception.ResultInvalidException;
import com.gamehub.model.Match;
import com.gamehub.model.Result;
import com.gamehub.model.Tournament;
import com.gamehub.model.User;
import com.gamehub.repository.MatchRepository;
import com.gamehub.repository.TournamentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final TournamentRepository tournamentRepository;

    @Transactional
    @Override
    public void generateMatches(UUID tournamentId) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Torneo no encontrado"));

        List<User> players = tournament.getPlayers();
        Collections.shuffle(players);

        List<Match> matches = matchRepository.findAllByTournament_Id(tournamentId);
        int round = 0;
        if (!matches.isEmpty()) {
            int maxRound = matches.stream()
                    .mapToInt(Match::getRound)
                    .max()
                    .orElse(round);

            if (maxRound >= round) {
                round = maxRound + 1;
            }
        }

        for (int i = 0; i < players.size() - 1; i += 2) {
            Match match = new Match();
            match.setId(UUID.randomUUID());
            match.setPlayer1(players.get(i));
            match.setPlayer2(players.get(i + 1));
            match.setResult(Result.PENDING);
            match.setTournament(tournament);
            match.setRound(round);

            matchRepository.save(match);
        }
    }

    @Override
    public MatchResponseDto getMatchById(UUID id) {
        Match match = matchRepository.findById(id)
            .orElseThrow(() -> new MatchNotFoundException("Partida no encontrada"));

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
                .orElseThrow(() -> new MatchNotFoundException("Partida no encontrada"));

        try {
            match.setResult(Result.valueOf(dto.getResult()));
        } catch (IllegalArgumentException e) {
            throw new ResultInvalidException("Valor inválido para el resultado: " + dto.getResult() + ". Prueba con: " + Result.PLAYER1_WIN + " o " + Result.PLAYER2_WIN);
        }

        matchRepository.save(match);
    }
}