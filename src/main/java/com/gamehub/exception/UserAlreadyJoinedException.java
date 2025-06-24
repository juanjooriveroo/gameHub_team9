package com.gamehub.exception;

import java.util.UUID;

public class UserAlreadyJoinedException extends RuntimeException {
    public UserAlreadyJoinedException(UUID userId, UUID tournamentId) {
        super(
            String.format("User with ID %s has already joined the tournament with ID %s", userId, tournamentId)
        );
    }
}
