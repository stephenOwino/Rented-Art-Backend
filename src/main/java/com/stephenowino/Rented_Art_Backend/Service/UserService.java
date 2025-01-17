package com.stephenowino.Rented_Art_Backend.Service;

import com.stephenowino.Rented_Art_Backend.Entity.User;
import com.stephenowino.Rented_Art_Backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        // Register a new user (encrypt password before saving)
        @Transactional
        public User registerUser(String firstName, String lastName, String email, String password, String role, String bio) {
                if (userRepository.findByEmail(email).isPresent()) {
                        throw new RuntimeException("Email is already taken");
                }

                // Convert the role string to Role enum
                User.Role userRole = User.Role.valueOf(role.toUpperCase()); /* Convert to upper case to match enum*/

                String encryptedPassword = passwordEncoder.encode(password);

                User newUser = User.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .email(email)
                        .password(encryptedPassword)
                        .role(userRole) // Set role
                        .bio(bio) // Set bio
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

        // Get the current logged-in user's profile
        public User getUserProfile() {
                String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

                // Fetch the user from the database
                Optional<User> userOpt = userRepository.findByEmail(email);
                if (userOpt.isEmpty()) {
                        throw new RuntimeException("User not found");
                }

                return userOpt.get();
        }

        // Update the current logged-in user's profile (bio and profile picture)
        @Transactional
        public User updateUserProfile(String bio, String profilePicture) {
                String email = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();

                // Fetch the user from the database
                Optional<User> userOpt = userRepository.findByEmail(email);
                if (userOpt.isEmpty()) {
                        throw new RuntimeException("User not found");
                }

                User user = userOpt.get();

                // Update the bio and profile picture
                user.setBio(bio);

                return userRepository.save(user);
        }
        // Invalidate the current session or token (if applicable)
        public void logoutUser() {
                SecurityContextHolder.clearContext();
        }

}

