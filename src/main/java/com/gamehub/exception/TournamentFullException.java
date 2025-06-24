package com.gamehub.exception;

import java.util.UUID;

public class TournamentFullException extends RuntimeException {
    public TournamentFullException(UUID id) {

        super("Tournament with id " + id + " is full and cannot accept more participants.");
    }
}
