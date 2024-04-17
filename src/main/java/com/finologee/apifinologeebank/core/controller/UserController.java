package com.finologee.apifinologeebank.core.controller;

import com.finologee.apifinologeebank.core.dto.UserInformationDto;
import com.finologee.apifinologeebank.core.model.User;
import com.finologee.apifinologeebank.core.service.UserService;
import com.finologee.apifinologeebank.core.util.LoggedUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "${base_url}/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;



    @GetMapping
    public ResponseEntity<Optional<User>> getUserByUsername(@RequestParam String username) {
        Optional<User> users = userService.getUserByUsername(username);
        return ResponseEntity.ok(users);
    }


    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        Optional<User> optionalUser = userService.getUserById(id);
        return optionalUser.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PatchMapping
    public ResponseEntity<?> patchUser(@RequestBody UserInformationDto user) {
        userService.patchUser(user);
        return ResponseEntity.ok("User Updated Successfully ...");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
