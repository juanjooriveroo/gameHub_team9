package com.gamehub.controller.matches;

import com.gamehub.dto.match.*;
import com.gamehub.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @PostMapping("/generate/{tournamentId}")
    public ResponseEntity<Void> generateMatches(@PathVariable UUID tournamentId) {
        matchService.generateMatches(tournamentId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchResponseDto> getMatch(@PathVariable UUID id) {
        return ResponseEntity.ok(matchService.getMatchById(id));
    }

    @PutMapping("/{id}/result")
    public ResponseEntity<Void> updateResult(@PathVariable UUID id, @RequestBody MatchResultUpdateDto dto) {
        matchService.updateMatchResult(id, dto);
        return ResponseEntity.ok().build();
    }
}
