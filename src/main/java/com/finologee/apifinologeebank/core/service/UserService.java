package com.finologee.apifinologeebank.core.service;

import com.finologee.apifinologeebank.core.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface UserService {
    Optional<User> getUserById(UUID id);

    User createUser(User user);

    User updateUser(UUID id, User user);

    void deleteUser(UUID id);

    List<User> getAllUsers();

    Optional<User> getUserByUsername(String authenticatedUsername);
}
