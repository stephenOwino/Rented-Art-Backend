package com.stephenowino.Rented_Art_Backend.Controller;

import com.stephenowino.Rented_Art_Backend.Entity.Artist;
import com.stephenowino.Rented_Art_Backend.Entity.Renter;
import com.stephenowino.Rented_Art_Backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

        @Autowired
        private UserService userService;

        // Register Artist
        @PostMapping("/register/artist")
        public ResponseEntity<String> registerArtist(@RequestBody Artist artist) {
                return registerUser(artist.getUsername(), artist.getPassword(), artist, "Artist");
        }

        // Register Renter
        @PostMapping("/register/renter")
        public ResponseEntity<String> registerRenter(@RequestBody Renter renter) {
                return registerUser(renter.getUsername(), renter.getPassword(), renter, "Renter");
        }

        // Common registration logic for both Artist and Renter
        private <T> ResponseEntity<String> registerUser(String username, String password, T user, String userType) {
                try {
                        boolean isRegistered = userService.findByUsername(username) != null;
                        if (isRegistered) {
                                return new ResponseEntity<>(userType + " username already taken.", HttpStatus.CONFLICT);
                        }
                        userService.saveUser(user);
                        return new ResponseEntity<>(userType + " registered successfully!", HttpStatus.CREATED);
                } catch (Exception e) {
                        return new ResponseEntity<>("Error registering " + userType.toLowerCase() + ": " + e.getMessage(), HttpStatus.BAD_REQUEST);
                }
        }

        // Login for both Artist and Renter
        @PostMapping("/login")
        public ResponseEntity<String> loginUser(@RequestBody Object user) {
                try {
                        if (user instanceof Renter) {
                                return loginRenter((Renter) user);
                        } else if (user instanceof Artist) {
                                return loginArtist((Artist) user);
                        } else {
                                return new ResponseEntity<>("Invalid user type", HttpStatus.BAD_REQUEST);
                        }
                } catch (Exception e) {
                        return new ResponseEntity<>("Error logging in: " + e.getMessage(), HttpStatus.BAD_REQUEST);
                }
        }

        // Login for Renter
        private ResponseEntity<String> loginRenter(Renter renter) {
                return authenticateUser(renter.getUsername(), renter.getPassword(), "Renter");
        }

        // Login for Artist
        private ResponseEntity<String> loginArtist(Artist artist) {
                return authenticateUser(artist.getUsername(), artist.getPassword(), "Artist");
        }

        // Common login logic for both Artist and Renter
        private ResponseEntity<String> authenticateUser(String username, String password, String userType) {
                Object existingUser = userService.findByUsername(username);
                if (existingUser != null) {
                        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                        String storedPassword = userType.equals("Renter") ? ((Renter) existingUser).getPassword() : ((Artist) existingUser).getPassword();
                        if (passwordEncoder.matches(password, storedPassword)) {
                                return new ResponseEntity<>(userType + " login successful", HttpStatus.OK);
                        } else {
                                return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
                        }
                } else {
                        return new ResponseEntity<>(userType + " not found", HttpStatus.NOT_FOUND);
                }
        }

        // Get all Artists
        @GetMapping("/artists")
        public ResponseEntity<List<Artist>> getAllArtists() {
                return getAllUsers(userService.getAllArtists(), "Artist");
        }

        // Get all Renters
        @GetMapping("/renters")
        public ResponseEntity<List<Renter>> getAllRenters() {
                return getAllUsers(userService.getAllRenters(), "Renter");
        }

        // Generalized method to get all users (Artists or Renters)
        private <T> ResponseEntity<List<T>> getAllUsers(List<T> users, String userType) {
                if (users.isEmpty()) {
                        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                }
                return new ResponseEntity<>(users, HttpStatus.OK);
        }

        // Get a user by username (either Artist or Renter)
        @GetMapping("/{username}")
        public ResponseEntity<Object> getUserByUsername(@PathVariable String username) {
                Object user = userService.findByUsername(username);
                if (user != null) {
                        return new ResponseEntity<>(user, HttpStatus.OK);
                } else {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
        }
}
