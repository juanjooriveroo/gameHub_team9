package com.gamehub.controller;

import com.gamehub.service.MessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/api/matches/{id}/messages")
public class MatchesMessagesController {

    //Listar mensajes de la partida
    @GetMapping
    public String listMatchMessages(@PathVariable UUID id) {

        return null;
    }

    //Enviar mensajes a la partida
    @PostMapping
    public String sendMessageToMatch(@PathVariable UUID id) {

        return null;
    }

}
