package com.flarecon.mocktail.service;

import com.flarecon.mocktail.dto.user.UserCreateRequest;
import com.flarecon.mocktail.model.User;
import com.flarecon.mocktail.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User createUser(UserCreateRequest user) {
        return userRepository.save(new User(user));
    }

    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
