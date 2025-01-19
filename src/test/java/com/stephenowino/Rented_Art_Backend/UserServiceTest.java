package com.stephenowino.Rented_Art_Backend;

import com.stephenowino.Rented_Art_Backend.Entity.User;
import com.stephenowino.Rented_Art_Backend.Repository.UserRepository;
import com.stephenowino.Rented_Art_Backend.Service.UserService;
import com.stephenowino.Rented_Art_Backend.exception.UserNotFoundException;
import com.stephenowino.Rented_Art_Backend.Entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

public class UserServiceTest {

        @Mock
        private UserRepository userRepository;

        @Mock
        private PasswordEncoder passwordEncoder;

        @Mock
        private SecurityContext securityContext;

        @Mock
        private Authentication authentication;

        @InjectMocks
        private UserService userService;

        private User testUser;

        @BeforeEach
        void setUp() {
                MockitoAnnotations.openMocks(this);

                // Create a test user with the appropriate role
                testUser = new User("John", "Doe", "john.doe@example.com", "password", Role.RENTER, "Bio here");

                // Mock SecurityContext to avoid null pointer
                when(securityContext.getAuthentication()).thenReturn(authentication);
                SecurityContextHolder.setContext(securityContext);
                when(authentication.getPrincipal()).thenReturn(testUser);
        }

        @Test
        void shouldRegisterUserSuccessfully() {
                // Arrange
                when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.empty());
                when(passwordEncoder.encode(testUser.getPassword())).thenReturn("encodedPassword");
                when(userRepository.save(any(User.class))).thenReturn(testUser);

                // Act
                User registeredUser = userService.registerUser(
                        testUser.getFirstName(),
                        testUser.getLastName(),
                        testUser.getEmail(),
                        testUser.getPassword(),
                        Role.RENTER.name(),  // Pass Role enum name
                        testUser.getBio()
                );

                // Assert
                assertNotNull(registeredUser);
                assertEquals(testUser.getEmail(), registeredUser.getEmail());
                verify(userRepository, times(1)).save(any(User.class));
        }

        @Test
        void shouldThrowExceptionWhenRegisteringWithExistingEmail() {
                // Arrange
                when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));

                // Act & Assert
                RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
                        userService.registerUser(
                                testUser.getFirstName(),
                                testUser.getLastName(),
                                testUser.getEmail(),
                                testUser.getPassword(),
                                Role.RENTER.name(),  // Pass Role enum name
                                testUser.getBio()
                        );
                });
                assertEquals("Email is already taken", thrown.getMessage());
        }

        @Test
        void shouldLoginUserSuccessfully() {
                // Arrange
                when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));
                when(passwordEncoder.matches(testUser.getPassword(), "encodedPassword")).thenReturn(true);

                // Act
                User loggedInUser = userService.loginUser(testUser.getEmail(), testUser.getPassword());

                // Assert
                assertNotNull(loggedInUser);
                assertEquals(testUser.getEmail(), loggedInUser.getEmail());
                verify(userRepository, times(1)).findByEmail(testUser.getEmail());
        }

        @Test
        void shouldThrowExceptionWhenUserNotFoundOnLogin() {
                // Arrange
                when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.empty());

                // Act & Assert
                UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> {
                        userService.loginUser(testUser.getEmail(), testUser.getPassword());
                });
                assertEquals("User not found with email: " + testUser.getEmail(), thrown.getMessage());
        }

        @Test
        void shouldThrowExceptionWhenInvalidPasswordOnLogin() {
                // Arrange
                when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));
                when(passwordEncoder.matches("wrongPassword", "encodedPassword")).thenReturn(false);

                // Act & Assert
                RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
                        userService.loginUser(testUser.getEmail(), "wrongPassword");
                });
                assertEquals("Invalid credentials", thrown.getMessage()); // Ensure consistency with the method message
        }

        @Test
        void shouldLogoutUserSuccessfully() {
                // Act
                userService.logoutUser();

                // Assert
                verify(securityContext, times(1)).setAuthentication(null);
                verify(SecurityContextHolder.getContext(), times(1)).setAuthentication(null);
                // Also clear SecurityContext
                SecurityContextHolder.clearContext();
                verify(securityContext, times(1)).setAuthentication(null);  // Verify that logout clears the context
        }
}
