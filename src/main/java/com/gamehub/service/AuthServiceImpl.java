package com.gamehub.service;

import com.gamehub.dto.AuthResponseDto;
import com.gamehub.dto.LoginRequestDto;
import com.gamehub.dto.RegisterRequestDto;
import com.gamehub.exception.InvalidCredentialsException;
import com.gamehub.exception.UserNotFoundException;
import com.gamehub.model.Role;
import com.gamehub.model.User;
import com.gamehub.repository.UserRepository;
import com.gamehub.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public AuthResponseDto register(RegisterRequestDto request) {
        User user = new User();
        user.setId(UUID.randomUUID());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role roleToAssign = (request.getRole()  ==null) ? Role.PLAYER : request.getRole();
        user.setRole(roleToAssign);

        user.setRank("Novice");
        user.setPoints(0);

        userRepository.save(user);

        String token = jwtUtils.generateToken(user);
        return new AuthResponseDto(token);
    }

    @Override
    public AuthResponseDto login(LoginRequestDto request){
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(()-> new UserNotFoundException("User not found with email: " + request.getEmail()));
        if(!passwordEncoder.matches(request.getPassword() , user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        } else {
            String token = jwtUtils.generateToken(user);
            return new AuthResponseDto(token);
        }
    }

}
