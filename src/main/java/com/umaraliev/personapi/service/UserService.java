package com.umaraliev.personapi.service;

import com.umaraliev.personapi.model.User;
import com.umaraliev.personapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getUserRepository() {
        return userRepository.findAll();
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
