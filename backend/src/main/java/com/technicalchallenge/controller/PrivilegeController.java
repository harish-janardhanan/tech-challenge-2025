package com.technicalchallenge.controller;

import com.technicalchallenge.model.Privilege;
import com.technicalchallenge.service.PrivilegeService;
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
@RequestMapping("/api/privileges")
public class PrivilegeController {
    private static final Logger logger = LoggerFactory.getLogger(PrivilegeController.class);

    @Autowired
    private PrivilegeService privilegeService;

    @GetMapping
    public List<Privilege> getAllPrivileges() {
        logger.info("Fetching all privileges");
        return privilegeService.getAllPrivileges();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Privilege> getPrivilegeById(@PathVariable Long id) {
        logger.debug("Fetching privilege by id: {}", id);
        Optional<Privilege> privilege = privilegeService.getPrivilegeById(id);
        return privilege.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createPrivilege(@Valid @RequestBody Privilege privilege) {
        logger.info("Creating new privilege: {}", privilege);
        Privilege savedPrivilege = privilegeService.savePrivilege(privilege);
        return ResponseEntity.created(URI.create("/api/privileges/" + savedPrivilege.getId())).body(savedPrivilege);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrivilege(@PathVariable Long id) {
        logger.warn("Deleting privilege with id: {}", id);
        privilegeService.deletePrivilege(id);
        return ResponseEntity.noContent().build();
    }
}
