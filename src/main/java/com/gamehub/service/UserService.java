package com.gamehub.service;

import com.gamehub.dto.RegisterRequestDto;
import com.gamehub.dto.UserDto;
import com.gamehub.dto.UserPublicDto;
import com.gamehub.model.User;

import java.util.UUID;

public interface UserService {

    UserDto getCurrentUser();

    UserPublicDto getUserById(UUID userId);

    User getCurrentUserEntity();

}
