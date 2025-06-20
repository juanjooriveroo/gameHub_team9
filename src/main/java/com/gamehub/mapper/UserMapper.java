package com.gamehub.mapper;

import com.gamehub.dto.UserDto;
import com.gamehub.dto.UserPublicDto;
import com.gamehub.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setRank(user.getRank());
        dto.setPoints(user.getPoints());

        return dto;
    }

    public UserPublicDto userPublicDto(User user) {
        UserPublicDto dto = new UserPublicDto();
        dto.setUsername(user.getUsername());
        dto.setRank(user.getRank());
        dto.setPoints(user.getPoints());

        return dto;
    }


}
