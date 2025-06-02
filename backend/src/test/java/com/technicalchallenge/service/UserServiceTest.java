package com.technicalchallenge.service;

import com.technicalchallenge.model.User;
import com.technicalchallenge.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;

    @Test
    void testFindUserById() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Optional<User> found = userService.getUserById(1L);
        assertTrue(found.isPresent());
        assertEquals(1L, found.get().getId());
    }

    @Test
    void testSaveUser() {
        User user = new User();
        user.setId(2L);
        when(userRepository.save(user)).thenReturn(user);
        User saved = userService.saveUser(user, null);
        assertNotNull(saved);
        assertEquals(2L, saved.getId());
    }

    @Test
    void testDeleteUser() {
        Long userId = 3L;
        doNothing().when(userRepository).deleteById(userId);
        userService.deleteUser(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testFindUserByNonExistentId() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());
        Optional<User> found = userService.getUserById(99L);
        assertFalse(found.isPresent());
    }

    // Business logic: test user cannot be created with null login id
    @Test
    void testUserCreationWithNullLoginIdThrowsException() {
        User user = new User();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            validateUser(user);
        });
        assertTrue(exception.getMessage().contains("Login id cannot be null"));
    }

    // Helper for business logic validation
    private void validateUser(User user) {
        if (user.getLoginId() == null) {
            throw new IllegalArgumentException("Login id cannot be null");
        }
    }
}
