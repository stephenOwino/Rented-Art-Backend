package com.stephenowino.Rented_Art_Backend.Controller;

import com.stephenowino.Rented_Art_Backend.Entity.User;
import com.stephenowino.Rented_Art_Backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

        @Autowired
        private UserService userService;

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private BCryptPasswordEncoder passwordEncoder;

        // DTO for user registration
        public static class RegisterRequest {
                public String firstName;
                public String lastName;
                public String email;
                public String password;
                public String role;
                public String bio;
        }

        // DTO for user login
        public static class LoginRequest {
                public String email;
                public String password;
        }

        // Register a new user
        @PostMapping("/register")
        public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
                try {
                        // Proceed with registration
                        User newUser = userService.registerUser(
                                registerRequest.firstName,
                                registerRequest.lastName,
                                registerRequest.email,
                                registerRequest.password,
                                registerRequest.role, // role passed as string
                                registerRequest.bio // bio passed as string
                        );

                        return ResponseEntity.ok("Registration successful. Welcome, " + newUser.getFirstName() + "!");
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
                }
        }

        // Login an existing user
        @PostMapping("/login")
        public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
                try {
                        // Attempt authentication using the provided email and password
                        Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password)
                        );

                        // Set authentication context
                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        // Check if the user exists
                        Optional<User> user = userService.findUserByEmail(loginRequest.email);
                        if (user.isPresent()) {
                                return ResponseEntity.ok("Login successful. Welcome back, " + user.get().getFirstName() + "!");
                        } else {
                                // User is not found, prompt them to register
                                return ResponseEntity.badRequest().body("User with email '" + loginRequest.email + "' not found. Please register first.");
                        }
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body("Login failed: Invalid email or password.");
                }
        }

        // Get the current user's profile
        @GetMapping("/profile")
        public ResponseEntity<?> getProfile() {
                try {
                        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                        String email = authentication.getName();

                        Optional<User> user = userService.findUserByEmail(email);
                        if (user.isPresent()) {
                                return ResponseEntity.ok(user.get());
                        } else {
                                return ResponseEntity.badRequest().body("Profile not found for the current user.");
                        }
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body("An error occurred while retrieving the profile: " + e.getMessage());
                }
        }

        // Logout a user
        @PostMapping("/logout")
        public ResponseEntity<String> logout() {
                userService.logoutUser();
                return ResponseEntity.ok("User successfully logged out");
        }
}
