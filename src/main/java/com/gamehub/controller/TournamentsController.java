package com.gamehub.controller;

import com.gamehub.dto.tournament.TournamentDto;
import com.gamehub.dto.tournament.TournamentRequestDto;
import com.gamehub.dto.tournament.TournamentResponseDto;
import com.gamehub.exception.TournamentFullException;
import com.gamehub.exception.UnauthorizedException;
import com.gamehub.exception.UserAlreadyJoinedException;
import com.gamehub.model.Role;
import com.gamehub.model.Tournament;
import com.gamehub.model.User;
import com.gamehub.service.TournamentService;
import com.gamehub.service.TournamentServiceImpl;
import com.gamehub.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/tournaments")
@RequiredArgsConstructor
public class TournamentsController {

    private final TournamentService tournamentService;

    private  final UserService userService;

    //Crear nuevo torneo
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
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
    public ResponseEntity <TournamentDto> getTournamentById (@PathVariable UUID id) {

        TournamentDto tournamentById = tournamentService.getTournamentById(id);

        return ResponseEntity.ok(tournamentById);
    }

    //Unirse a torneo
    @PostMapping("/{id}/join")
    public ResponseEntity<?> joinTournament(@PathVariable UUID id) {
        UUID userId = userService.getCurrentUserEntity().getId();
        try {
            tournamentService.joinTournament(id, userId);
            return ResponseEntity.ok("Joined tournament successfully");
        } catch (UserAlreadyJoinedException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (TournamentFullException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (UnauthorizedException e){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected error");
        }
    }
}
