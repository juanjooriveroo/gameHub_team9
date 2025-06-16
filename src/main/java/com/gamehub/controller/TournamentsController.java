package com.gamehub.controller;

import com.gamehub.service.TournamentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentsController {

    //Crear nuevo torneo
    @PostMapping
    public String createTournament() {

        return null;
    }

    //Listar torneos
    @GetMapping
    public String listTournaments() {

        return null;
    }

    //Detalle de torneo
    @GetMapping("/{id}")
    public String getTournamentDetails(@PathVariable UUID id) {

        return null;
    }

    //Unirse a torneo
    @PostMapping("/{id}/join")
    public String joinTournament(@PathVariable UUID id) {

        return null;
    }
}
