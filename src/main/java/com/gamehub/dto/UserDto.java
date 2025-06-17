package com.gamehub.dto;

import lombok.Data;

@Data
public class UserDto {
    private String username;
    private String email;
    private Role role;
    private String rank;
    private int points;

}
