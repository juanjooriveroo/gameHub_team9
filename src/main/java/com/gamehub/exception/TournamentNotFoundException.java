package com.gamehub.exception;

import java.util.UUID;

public class TournamentNotFoundException extends RuntimeException {
    public TournamentNotFoundException(UUID id) {

        super("Tournament with id " + id + " not found.");
    }
}
