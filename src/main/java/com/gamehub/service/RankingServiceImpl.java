package com.gamehub.service;

import com.gamehub.dto.RankingDto;
import com.gamehub.exception.TournamentNotFoundException;
import com.gamehub.mapper.RankingMapper;
import com.gamehub.model.Match;
import com.gamehub.model.Tournament;
import com.gamehub.model.User;
import com.gamehub.repository.MatchRepository;
import com.gamehub.repository.TournamentRepository;
import com.gamehub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {
    private final MatchRepository matchRepository;
    private final TournamentRepository tournamentRepository;
    private final UserRepository userRepository;
    private final RankingMapper rankingMapper;


    @Override
    public RankingDto getRanking(UUID id) {
        Tournament tournament = updateTournamentPlayerPoints(id);

        return rankingMapper.toRanking(tournament);
    }

    private Tournament updateTournamentPlayerPoints(UUID id) {
        Tournament tournament = tournamentRepository.findById(id)
                .orElseThrow(() -> new TournamentNotFoundException("Tournament " + id + " not found."));

        tournament.getPlayers().forEach(player -> {
            setPoints(player, tournament.getId());
        });

        return tournamentRepository.findById(tournament.getId())
                .orElseThrow(() -> new TournamentNotFoundException("Tournament " + tournament.getId() + " not found."));
    }

    private void setPoints(User player, UUID tournamentId) {
        player.setPoints(0);

        List<Match> matches = matchRepository.findByTournament_Id(tournamentId);

        for (Match match : matches) {
            if (player.getId().equals(match.getPlayer1().getId())) {
                switch (match.getResult()) {
                    case PLAYER1_WIN -> player.setPoints(player.getPoints() + 3);
                    case PLAYER2_WIN -> player.setPoints(player.getPoints() - 1);
                }
            } else if (player.getId().equals(match.getPlayer2().getId())) {
                switch (match.getResult()) {
                    case PLAYER2_WIN -> player.setPoints(player.getPoints() + 3);
                    case PLAYER1_WIN -> player.setPoints(player.getPoints() - 1);
                }
            }
        }

        userRepository.save(player);
    }
}