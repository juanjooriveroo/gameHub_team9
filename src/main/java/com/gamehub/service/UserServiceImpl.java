package com.gamehub.service;

import com.gamehub.dto.RegisterRequestDto;
import com.gamehub.dto.UserDto;
import com.gamehub.dto.UserPublicDto;
import com.gamehub.exception.UserNotFoundException;
import com.gamehub.model.User;
import com.gamehub.repository.UserRepository;
import com.gamehub.security.JwtUtils;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    //Método para obetener los datos del usuario autenticado
    @Override
    public UserDto getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = (UUID) auth.getPrincipal();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        UserDto userDto =new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setRole(user.getRole());
        userDto.setRank(user.getRank());
        userDto.setPoints(user.getPoints());

        return userDto;
    }

    //Método para obtener el perfil público de un usuario por ID
    @Override
    public UserPublicDto getUserById(UUID id){
        User user = userRepository.findById(id)
                .orElseThrow(()-> new UserNotFoundException("User not found."));

        UserPublicDto dto = new UserPublicDto();
        dto.setUsername(user.getUsername());
        dto.setRank(user.getRank());
        dto.setPoints(user.getPoints());

        return dto;
    }


}
