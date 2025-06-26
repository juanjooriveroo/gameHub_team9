package com.gamehub.exception;

import java.util.UUID;

public class UserAlreadyJoinedException extends RuntimeException {
    public UserAlreadyJoinedException(String message) {
        super(message);
    }
}
