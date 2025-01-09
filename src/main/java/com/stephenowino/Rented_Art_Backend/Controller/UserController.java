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

        // Register a new user
        @PostMapping("/register")
        public ResponseEntity<?> registerUser(@RequestParam String firstName,
                                              @RequestParam String lastName,
                                              @RequestParam String email,
                                              @RequestParam String password,
                                              @RequestParam String confirmPassword,  // Added confirmPassword
                                              @RequestParam User.Role role) {
                try {
                        if (!password.equals(confirmPassword)) {
                                return ResponseEntity.badRequest().body("Error: Passwords do not match");
                        }
                        // Proceed to register user
                        User newUser = userService.registerUser(firstName, lastName, email, password, role);
                        return ResponseEntity.ok(newUser);
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
                }
        }

        // Login an existing user
        @PostMapping("/login")
        public ResponseEntity<?> loginUser(@RequestParam String email,
                                           @RequestParam String password) {
                try {
                        User user = userService.loginUser(email, password);

                        // Authenticate the user
                        Authentication authentication = authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(email, password)
                        );

                        SecurityContextHolder.getContext().setAuthentication(authentication);

                        return ResponseEntity.ok(user);  // No token, just return the user details
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
                }
        }

        // Get current user's profile
        @GetMapping("/profile")
        public ResponseEntity<?> getProfile() {
                try {
                        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                        String email = authentication.getName();
                        Optional<User> userOpt = userService.findUserByEmail(email);
                        if (userOpt.isEmpty()) {
                                return ResponseEntity.badRequest().body("User not found");
                        }

                        return ResponseEntity.ok(userOpt.get());
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
                }
        }
}
