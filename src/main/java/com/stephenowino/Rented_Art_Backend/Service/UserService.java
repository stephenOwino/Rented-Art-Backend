package com.stephenowino.Rented_Art_Backend.Service;

import com.stephenowino.Rented_Art_Backend.Entity.User;
import com.stephenowino.Rented_Art_Backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PasswordEncoder passwordEncoder; // Use PasswordEncoder instead of BCryptPasswordEncoder

        // Register a new user (encrypt password before saving)
        @Transactional
        public User registerUser(String firstName, String lastName, String email, String password, String confirmPassword, User.Role role) {
                if (userRepository.findByEmail(email).isPresent()) {
                        throw new RuntimeException("Email is already taken");
                }

                // Check if passwords match
                if (!password.equals(confirmPassword)) {
                        throw new RuntimeException("Passwords do not match");
                }

                String encryptedPassword = passwordEncoder.encode(password);

                User newUser = User.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .password(encryptedPassword)
                        .role(role)
                        .build();

                return userRepository.save(newUser);
        }

        // Login a user (verify email and password)
        public User loginUser(String email, String password) {
                Optional<User> userOpt = userRepository.findByEmail(email);
                if (userOpt.isEmpty()) {
                        throw new RuntimeException("User not found");
                }

                User user = userOpt.get();

                if (!passwordEncoder.matches(password, user.getPassword())) {
                        throw new RuntimeException("Invalid credentials");
                }

                return user;
        }

        // Find a user by email
        public Optional<User> findUserByEmail(String email) {
                return userRepository.findByEmail(email);
        }

        // Find a user by ID
        public Optional<User> findUserById(Long id) {
                return userRepository.findById(id);
        }
}
