package com.gamehub.controller.tournament;

import com.gamehub.service.TournamentService;
import com.gamehub.service.TournamentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/tournaments/{id}/ranking")
public class TournamentsRankingController {

    // Obtener clasificación actual
    @GetMapping
    public String getTournamentRanking(@PathVariable UUID id) {

        return null;
    }


}
