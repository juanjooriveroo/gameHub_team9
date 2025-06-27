package com.gamehub.controller.tournament;

import com.gamehub.service.MessageServiceImpl;
import com.gamehub.service.TournamentService;
import com.gamehub.service.TournamentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/tournaments/{id}/messages")
public class TournamentsMessagesController {

    //Listar mensajes del torneo
    @GetMapping
    @PreAuthorize("hasAnyRole('PLAYER', 'ADMIN')")
    public String listTournamentMessages(@PathVariable UUID id) {

        return null;
    }

    //Enviar mensaje al torneo
    @PostMapping
    public String sendMessageToTournament(@PathVariable UUID id) {

        return null;
    }

}
