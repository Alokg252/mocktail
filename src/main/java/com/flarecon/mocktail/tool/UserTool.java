package com.flarecon.mocktail.tool;

import com.flarecon.mocktail.dto.user.UserCreateRequest;
import com.flarecon.mocktail.model.User;
import com.flarecon.mocktail.repository.UserRepository;
import com.flarecon.mocktail.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTool {
    private final UserRepository userRepository;
    private final UserService userService;

    @Tool(name = "createUser", description = "Create a new user")
    public User createUser(UserCreateRequest user) {
        return userService.createUser(user);
    }

    @Tool(name = "listAllUsers", description = "List All Users")
    public List<User> listAllUsers() {
        return userService.getAllUsers();
    }

}
