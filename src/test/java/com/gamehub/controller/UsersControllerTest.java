package com.gamehub.controller;

import com.gamehub.dto.UserDto;
import com.gamehub.dto.UserPublicDto;
import com.gamehub.exception.BadTokenException;
import com.gamehub.exception.UserNotFoundException;
import com.gamehub.model.Role;
import com.gamehub.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    @Mock
    private UserServiceImpl userService;

    @InjectMocks
    private UsersController usersController;

    private UserDto userDto;
    private UserPublicDto userPublicDto;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        userDto = new UserDto();
        userDto.setId(userId);
        userDto.setUsername("testuser");
        userDto.setEmail("test@example.com");
        userDto.setRole(Role.PLAYER);
        userDto.setRank("Bronze");
        userDto.setPoints(100);

        userPublicDto = new UserPublicDto();
        userPublicDto.setUsername("testuser");
        userPublicDto.setRank("Bronze");
        userPublicDto.setPoints(100);
    }

    @Test
    void getCurrentUser_ValidToken_ReturnsUser() {
        when(userService.getCurrentUser()).thenReturn(userDto);

        ResponseEntity<UserDto> response = usersController.getCurrentUser();

        assertEquals(userDto, response.getBody());
        verify(userService).getCurrentUser();
    }

    @Test
    void getCurrentUser_InvalidToken_ThrowsException() {
        when(userService.getCurrentUser()).thenThrow(new BadTokenException("Invalid token"));

        assertThrows(BadTokenException.class, () -> {
            usersController.getCurrentUser();
        });

        verify(userService).getCurrentUser();
    }

    @Test
    void getUserProfile_ValidId_ReturnsPublicProfile() {
        when(userService.getUserById(userId)).thenReturn(userPublicDto);

        ResponseEntity<UserPublicDto> response = usersController.getUserProfile(userId);

        assertEquals(userPublicDto, response.getBody());
        verify(userService).getUserById(userId);
    }

    @Test
    void getUserProfile_InvalidId_ThrowsException() {
        UUID invalidId = UUID.randomUUID();
        when(userService.getUserById(invalidId))
                .thenThrow(new UserNotFoundException("User not found"));

        assertThrows(UserNotFoundException.class, () -> {
            usersController.getUserProfile(invalidId);
        });
    }
}