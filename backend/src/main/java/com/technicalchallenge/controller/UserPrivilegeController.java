package com.technicalchallenge.controller;

import com.technicalchallenge.model.UserPrivilege;
import com.technicalchallenge.service.UserPrivilegeService;
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
@RequestMapping("/api/user-privileges")
public class UserPrivilegeController {
    private static final Logger logger = LoggerFactory.getLogger(UserPrivilegeController.class);

    @Autowired
    private UserPrivilegeService userPrivilegeService;

    @GetMapping
    public List<UserPrivilege> getAllUserPrivileges() {
        logger.info("Fetching all user privileges");
        return userPrivilegeService.getAllUserPrivileges();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPrivilege> getUserPrivilegeById(@PathVariable Long id) {
        logger.debug("Fetching user privilege by id: {}", id);
        Optional<UserPrivilege> userPrivilege = userPrivilegeService.getUserPrivilegeById(id);
        return userPrivilege.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createUserPrivilege(@Valid @RequestBody UserPrivilege userPrivilege) {
        logger.info("Creating new user privilege: {}", userPrivilege);
        UserPrivilege createdUserPrivilege = userPrivilegeService.saveUserPrivilege(userPrivilege);
        return ResponseEntity.created(URI.create("/api/user-privileges/" + createdUserPrivilege.getUserId())).body(createdUserPrivilege);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserPrivilege(@PathVariable Long id) {
        logger.warn("Deleting user privilege with id: {}", id);
        userPrivilegeService.deleteUserPrivilege(id);
        return ResponseEntity.noContent().build();
    }
}
