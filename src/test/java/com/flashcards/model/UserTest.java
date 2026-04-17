package com.flashcards.model;

import java.time.LocalDateTime;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class UserTest {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    @Test
    void constructor_WithParameters_SetsFieldsCorrectly() {
        // Arrange & Act
        User user = new User("test@example.com", "password123");

        // Assert
        assertEquals("test@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertNotNull(user.getCreatedAt());
    }

    @Test
    void defaultConstructor_SetsCreatedAt() {
        // Arrange & Act
        User user = new User();

        // Assert
        assertNotNull(user.getCreatedAt());
    }

    @Test
    void validation_ValidUser_NoViolations() {
        // Arrange
        User user = new User("valid@example.com", "password123");

        // Act
        var violations = validator.validate(user);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void validation_InvalidEmail_Violation() {
        // Arrange
        User user = new User("invalid-email", "password123");

        // Act
        var violations = validator.validate(user);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getPropertyPath().toString().equals("email")));
    }

    @Test
    void validation_BlankEmail_Violation() {
        // Arrange
        User user = new User("", "password123");

        // Act
        var violations = validator.validate(user);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("must not be blank") || v.getMessage().contains("must be a well-formed email")));
    }

    @Test
    void validation_BlankPassword_Violation() {
        // Arrange
        User user = new User("test@example.com", "");

        // Act
        var violations = validator.validate(user);

        // Assert
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("must not be blank")));
    }

    @Test
    void userDetailsMethods_ReturnCorrectValues() {
        // Arrange
        User user = new User("test@example.com", "password123");

        // Act & Assert
        assertEquals("test@example.com", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
    }

    @Test
    void getAuthorities_ReturnsEmptyList() {
        // Arrange
        User user = new User("test@example.com", "password123");

        // Act
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        // Assert
        assertNotNull(authorities);
        assertTrue(authorities.isEmpty());
    }

    @Test
    void gettersAndSetters_WorkCorrectly() {
        // Arrange
        User user = new User();
        LocalDateTime now = LocalDateTime.now();

        // Act
        user.setId(1L);
        user.setEmail("new@example.com");
        user.setPassword("newpassword");
        user.setCreatedAt(now);

        // Assert
        assertEquals(1L, user.getId());
        assertEquals("new@example.com", user.getEmail());
        assertEquals("newpassword", user.getPassword());
        assertEquals(now, user.getCreatedAt());
    }
}