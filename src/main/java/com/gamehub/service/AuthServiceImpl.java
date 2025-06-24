package com.gamehub.service;

import com.gamehub.dto.AuthResponseDto;
import com.gamehub.dto.LoginRequestDto;
import com.gamehub.dto.RegisterRequestDto;
import com.gamehub.exception.EmailAlreadyInUseExcepcion;
import com.gamehub.exception.InvalidCredentialsException;
import com.gamehub.exception.UserAlreadyInUseExcepcion;
import com.gamehub.exception.UserNotFoundException;
import com.gamehub.model.Role;
import com.gamehub.model.User;
import com.gamehub.repository.UserRepository;
import com.gamehub.security.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Transactional
    @Override
    public AuthResponseDto register(RegisterRequestDto request) {
        log.info("Registro de nuevo usuario iniciado para email: {}", request.getEmail());

        if (userRepository.existsByEmail(request.getEmail())) {
            log.error("El email ya está en uso: {}", request.getEmail());
            throw new EmailAlreadyInUseExcepcion("Email already in use");
        }

        if (userRepository.existsUserByUsername(request.getUsername())) {
            log.error("El nombre de usuario ya está en uso: {}", request.getUsername());
            throw new UserAlreadyInUseExcepcion("Username already in use");
        }

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
        log.info("Usuario registrado correctamente: {}", user.getUsername());

        String token = jwtUtils.generateToken(user);
        log.info("Token JWT generado para usuario: {}", user.getUsername());
        return new AuthResponseDto(token);
    }

    @Transactional
    @Override
    public AuthResponseDto login(LoginRequestDto request){
        log.info("Inicio de sesión para email: {}", request.getEmail());

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    log.error("Usuario no encontrado con email: {}", request.getEmail());
                    return new UserNotFoundException("User not found with email: " + request.getEmail());
                });

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            log.error("Credenciales inválidas para email: {}", request.getEmail());
            throw new InvalidCredentialsException("Invalid email or password");
        } else {
            String token = jwtUtils.generateToken(user);
            log.info("Login exitoso, token generado para usuario: {}", user.getUsername());
            return new AuthResponseDto(token);
        }
    }
}