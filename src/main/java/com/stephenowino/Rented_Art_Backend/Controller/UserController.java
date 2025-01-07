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

        // Endpoint to register a new user (Artist or Renter)
        @PostMapping("/register")
        public ResponseEntity<String> registerUser(@RequestBody Object user) {
                try {
                        boolean isRegistered = userService.findByUsername(((Artist) user).getUsername()) != null ||
                                userService.findByUsername(((Renter) user).getUsername()) != null;

                        if (isRegistered) {
                                return new ResponseEntity<>("Username already taken.", HttpStatus.CONFLICT);
                        }
                        userService.saveUser(user);
                        return new ResponseEntity<>("User registered successfully!", HttpStatus.CREATED);
                } catch (Exception e) {
                        return new ResponseEntity<>("Error registering user: " + e.getMessage(), HttpStatus.BAD_REQUEST);
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

