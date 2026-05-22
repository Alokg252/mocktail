package com.flarecon.mocktail.tool;

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
    public User createUser(User user) {
        return userService.saveUser(user);
    }

    @Tool(name = "listAllUsers", description = "List All Users")
    public List<User> listAllUsers() {
        return userService.getAllUsers();
    }

}
