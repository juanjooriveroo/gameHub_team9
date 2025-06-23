package com.gamehub.service;

import com.gamehub.dto.RegisterRequestDto;
import com.gamehub.dto.UserDto;
import com.gamehub.dto.UserPublicDto;
import com.gamehub.exception.BadTokenException;
import com.gamehub.exception.UserNotFoundException;
import com.gamehub.model.User;
import com.gamehub.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Transactional
    @Override
    public UserDto getCurrentUser(){
        log.info("Obteniendo usuario actual desde contexto de seguridad");

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || auth.getPrincipal() == null) {
            log.error("Token inválido o no proporcionado en la solicitud");
            throw new BadTokenException("Token inválido o no proporcionado");
        }

        UUID userId = (UUID) auth.getPrincipal();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado con ID: {}", userId);
                    return new UserNotFoundException("User not found.");
                });

        log.info("Usuario actual obtenido: {}", user.getUsername());

        UserDto userDto =new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        userDto.setRank(user.getRank());
        userDto.setPoints(user.getPoints());

        return userDto;
    }

    @Transactional
    @Override
    public UserPublicDto getUserById(UUID id){
        log.info("Buscando usuario público por ID: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(()-> {
                    log.warn("Usuario no encontrado con ID: {}", id);
                    return new UserNotFoundException("User not found.");
                });

        UserPublicDto dto = new UserPublicDto();
        dto.setUsername(user.getUsername());
        dto.setRank(user.getRank());
        dto.setPoints(user.getPoints());

        log.info("Usuario público encontrado: {}", user.getUsername());
        return dto;
    }

    @Transactional
    @Override
    public User getCurrentUserEntity() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = (UUID) auth.getPrincipal();

        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found."));
    }



}
