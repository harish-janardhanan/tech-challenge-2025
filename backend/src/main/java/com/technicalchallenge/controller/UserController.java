package com.technicalchallenge.controller;

import com.technicalchallenge.model.ApplicationUser;
import com.technicalchallenge.service.ApplicationUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private ApplicationUserService applicationUserService;

    @GetMapping
    public List<ApplicationUser> getAllUsers() {
        logger.info("Fetching all users");
        return applicationUserService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationUser> getUserById(@PathVariable Long id) {
        logger.debug("Fetching user by id: {}", id);
        Optional<ApplicationUser> user = applicationUserService.getUserById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody ApplicationUser user) {
        logger.info("Creating new user: {}", user);
        ApplicationUser savedUser = applicationUserService.saveUser(user);
        return ResponseEntity.created(URI.create("/api/users/" + savedUser.getId())).body(savedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        logger.warn("Deleting user with id: {}", id);
        applicationUserService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
