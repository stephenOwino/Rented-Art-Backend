package com.stephenowino.Rented_Art_Backend.Controller;

import com.stephenowino.Rented_Art_Backend.Entity.Artist;
import com.stephenowino.Rented_Art_Backend.Entity.Renter;
import com.stephenowino.Rented_Art_Backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

        @Autowired
        private UserService userService;

        // Endpoint to register a new Artist
        @PostMapping("/register/artist")
        public ResponseEntity<String> registerArtist(@RequestBody Artist artist) {
                try {
                        boolean isRegistered = userService.findByUsername(artist.getUsername()) != null;
                        if (isRegistered) {
                                return new ResponseEntity<>("Username already taken.", HttpStatus.CONFLICT);
                        }
                        userService.saveUser(artist);
                        return new ResponseEntity<>("Artist registered successfully!", HttpStatus.CREATED);
                } catch (Exception e) {
                        return new ResponseEntity<>("Error registering artist: " + e.getMessage(), HttpStatus.BAD_REQUEST);
                }
        }

        // Endpoint to register a new Renter
        @PostMapping("/register/renter")
        public ResponseEntity<String> registerRenter(@RequestBody Renter renter) {
                try {
                        boolean isRegistered = userService.findByUsername(renter.getUsername()) != null;
                        if (isRegistered) {
                                return new ResponseEntity<>("Username already taken.", HttpStatus.CONFLICT);
                        }
                        userService.saveUser(renter);
                        return new ResponseEntity<>("Renter registered successfully!", HttpStatus.CREATED);
                } catch (Exception e) {
                        return new ResponseEntity<>("Error registering renter: " + e.getMessage(), HttpStatus.BAD_REQUEST);
                }
        }

        // Endpoint to login (checks username and password)
        @PostMapping("/login")
        public ResponseEntity<String> loginUser(@RequestBody Renter renter) {
                try {
                        // Check if user exists
                        Renter existingRenter = (Renter) userService.findByUsername(renter.getUsername());
                        if (existingRenter != null) {
                                // Compare password (Note: Add hashing logic here if passwords are hashed in DB)
                                if (existingRenter.getPassword().equals(renter.getPassword())) {
                                        return new ResponseEntity<>("Login successful", HttpStatus.OK);
                                } else {
                                        return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
                                }
                        } else {
                                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
                        }
                } catch (Exception e) {
                        return new ResponseEntity<>("Error logging in: " + e.getMessage(), HttpStatus.BAD_REQUEST);
                }
        }


        // Endpoint to find a user by username (can be Artist or Renter)
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

