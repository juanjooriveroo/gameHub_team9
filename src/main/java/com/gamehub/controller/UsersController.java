package com.gamehub.controller;

import com.gamehub.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    //Datos del usuario autenticado
    @PostMapping("/me")
    public String getAuthenticatedUser() {

        return null;
    }

    //Perfil público de un usuario
    @PostMapping("/{id}")
    public String getUserProfile(@PathVariable UUID id) {

        return null;
    }
}
