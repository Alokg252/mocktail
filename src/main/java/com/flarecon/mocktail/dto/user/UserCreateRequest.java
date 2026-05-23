package com.flarecon.mocktail.dto.user;

import lombok.Data;

@Data
public class UserCreateRequest {
    String name;
    String username;
    String password;
}
