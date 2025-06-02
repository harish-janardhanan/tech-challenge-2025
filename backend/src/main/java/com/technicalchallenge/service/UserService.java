package com.technicalchallenge.service;

import com.technicalchallenge.model.ApplicationUser;
import com.technicalchallenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public List<ApplicationUser> getAllUsers() {
        logger.info("Retrieving all users");
        return userRepository.findAll();
    }

    public Optional<ApplicationUser> getUserById(Long id) {
        logger.debug("Retrieving user by id: {}", id);
        return userRepository.findById(id);
    }

    public ApplicationUser saveUser(ApplicationUser user) {
        logger.info("Saving user: {}", user);
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        logger.warn("Deleting user with id: {}", id);
        userRepository.deleteById(id);
    }
}
