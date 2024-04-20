package com.finologee.apifinologeebank.core.service;

import com.finologee.apifinologeebank.core.dto.UserInformationDto;
import com.finologee.apifinologeebank.core.exception.NotFoundException;
import com.finologee.apifinologeebank.core.model.User;
import com.finologee.apifinologeebank.core.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User patchUser(UserInformationDto user) {
        User existingUser = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication()
                                                                               .getName())
                                          .orElseThrow(() -> new NotFoundException("User not found"));
        if (Objects.nonNull(user.getPassword()))
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        if (Objects.nonNull(user.getAddress()))
            existingUser.setAddress(user.getAddress());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserByUsername(String authenticatedUsername) {
        return userRepository.findByUsername(authenticatedUsername);
    }
}
