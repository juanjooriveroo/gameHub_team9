package com.gamehub.service;

import com.gamehub.dto.UserDto;
import com.gamehub.dto.UserPublicDto;
import com.gamehub.exception.BadTokenException;
import com.gamehub.exception.UserNotFoundException;
import com.gamehub.model.Role;
import com.gamehub.model.User;
import com.gamehub.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();

        user = new User();
        user.setId(userId);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setRole(Role.PLAYER);
        user.setRank("Gold");
        user.setPoints(500);
    }

    @Test
    void getCurrentUser_ValidAuthentication_ReturnsUserDto() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userId);
        when(authentication.isAuthenticated()).thenReturn(true); // ¡Esta línea es crucial!

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserDto result = userService.getCurrentUser();

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getRole(), result.getRole());
        assertEquals(user.getRank(), result.getRank());
        assertEquals(user.getPoints(), result.getPoints());
    }

    @Test
    void getCurrentUser_InvalidAuthentication_ThrowsException() {
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        assertThrows(BadTokenException.class, () -> {
            userService.getCurrentUser();
        });
    }

    @Test
    void getCurrentUser_UserNotFound_ThrowsException() {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userId);
        when(authentication.isAuthenticated()).thenReturn(true);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getCurrentUser();
        });
    }

    @Test
    void getUserById_ValidId_ReturnsPublicDto() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        UserPublicDto result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals(user.getUsername(), result.getUsername());
        assertEquals(user.getRank(), result.getRank());
        assertEquals(user.getPoints(), result.getPoints());
    }

    @Test
    void getUserById_InvalidId_ThrowsException() {
        UUID invalidId = UUID.randomUUID();
        when(userRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> {
            userService.getUserById(invalidId);
        });
    }
}