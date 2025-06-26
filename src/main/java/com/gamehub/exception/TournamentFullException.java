package com.gamehub.exception;

import java.util.UUID;

public class TournamentFullException extends RuntimeException {
    public TournamentFullException(String message) {
        super(message);
    }
}
