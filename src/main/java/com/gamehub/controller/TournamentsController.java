package com.gamehub.controller;

import com.gamehub.dto.tournament.TournamentRequestDto;
import com.gamehub.dto.tournament.TournamentResponseDto;
import com.gamehub.service.TournamentService;
import com.gamehub.service.TournamentServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tournaments")
@RequiredArgsConstructor
public class TournamentsController {

    private final TournamentService tournamentService;

    //Crear nuevo torneo
    @PostMapping
    @PreAuthorize("hasRole=('ADMIN')")
    public ResponseEntity<TournamentResponseDto> createTournament(@RequestBody TournamentRequestDto request) {
        TournamentResponseDto response = tournamentService.createTournament(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Listar torneos
    @GetMapping
    public ResponseEntity<List<TournamentResponseDto>> listTournaments() {
        List<TournamentResponseDto> tournaments = tournamentService.getAllTournaments();
        return ResponseEntity.ok(tournaments);
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
