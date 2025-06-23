package com.gamehub.controller;

import com.gamehub.dto.AuthResponseDto;
import com.gamehub.dto.LoginRequestDto;
import com.gamehub.dto.RegisterRequestDto;
import com.gamehub.exception.*;
import com.gamehub.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    private RegisterRequestDto registerRequest;
    private LoginRequestDto loginRequest;
    private AuthResponseDto authResponse;

    @BeforeEach
    void setUp() {
        registerRequest = new RegisterRequestDto();
        registerRequest.setUsername("testuser");
        registerRequest.setEmail("test@example.com");
        registerRequest.setPassword("password123");

        loginRequest = new LoginRequestDto();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        authResponse = new AuthResponseDto("jwt.token.here");
    }

    @Test
    void register_ValidRequest_ReturnsCreated() {
        when(authService.register(any(RegisterRequestDto.class))).thenReturn(authResponse);

        ResponseEntity<AuthResponseDto> response = authController.register(registerRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(authResponse, response.getBody());
        verify(authService).register(registerRequest);
    }

    @Test
    void register_EmailAlreadyExists_ThrowsException() {
        when(authService.register(any(RegisterRequestDto.class)))
                .thenThrow(new EmailAlreadyInUseExcepcion("Email already in use"));

        assertThrows(EmailAlreadyInUseExcepcion.class, () -> {
            authController.register(registerRequest);
        });
    }

    @Test
    void register_UsernameAlreadyExists_ThrowsException() {
        when(authService.register(any(RegisterRequestDto.class)))
                .thenThrow(new UserAlreadyInUseExcepcion("Username already in use"));

        assertThrows(UserAlreadyInUseExcepcion.class, () -> {
            authController.register(registerRequest);
        });
    }

    @Test
    void login_ValidCredentials_ReturnsOk() {
        when(authService.login(any(LoginRequestDto.class))).thenReturn(authResponse);

        ResponseEntity<AuthResponseDto> response = authController.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(authResponse, response.getBody());
        verify(authService).login(loginRequest);
    }

    @Test
    void login_InvalidCredentials_ThrowsException() {
        when(authService.login(any(LoginRequestDto.class)))
                .thenThrow(new InvalidCredentialsException("Invalid credentials"));

        assertThrows(InvalidCredentialsException.class, () -> {
            authController.login(loginRequest);
        });
    }

    @Test
    void login_UserNotFound_ThrowsException() {
        when(authService.login(any(LoginRequestDto.class)))
                .thenThrow(new UserNotFoundException("User not found"));

        assertThrows(UserNotFoundException.class, () -> {
            authController.login(loginRequest);
        });
    }
}