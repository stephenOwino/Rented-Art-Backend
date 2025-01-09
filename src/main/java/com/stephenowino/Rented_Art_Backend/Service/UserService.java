package com.stephenowino.Rented_Art_Backend.Service;

import com.stephenowino.Rented_Art_Backend.Entity.Artist;
import com.stephenowino.Rented_Art_Backend.Entity.Renter;
import com.stephenowino.Rented_Art_Backend.Repository.ArtistRepo;
import com.stephenowino.Rented_Art_Backend.Repository.RenterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

        @Autowired
        private ArtistRepo artistRepository;

        @Autowired
        private RenterRepo renterRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        // Refactored method to save user (either Artist or Renter)
        public Object saveUser(Object user) {
                if (user == null) {
                        throw new IllegalArgumentException("User object cannot be null.");
                }

                // Handle Artist registration
                if (user instanceof Artist) {
                        Artist artist = (Artist) user;

                        // Check if username already exists
                        if (findByUsername(artist.getUsername()) != null) {
                                throw new IllegalArgumentException("Username already taken.");
                        }

                        // Validate required fields
                        if (artist.getUsername() == null || artist.getUsername().isEmpty()) {
                                throw new IllegalArgumentException("Username is required.");
                        }
                        if (artist.getPassword() == null || artist.getPassword().isEmpty()) {
                                throw new IllegalArgumentException("Password is required.");
                        }
                        if (artist.getEmail() == null || artist.getEmail().isEmpty()) {
                                throw new IllegalArgumentException("Email is required.");
                        }

                        // Encode password and save the artist
                        artist.setPassword(passwordEncoder.encode(artist.getPassword()));
                        return artistRepository.save(artist);

                        // Handle Renter registration
                } else if (user instanceof Renter) {
                        Renter renter = (Renter) user;

                        // Check if username already exists
                        if (findByUsername(renter.getUsername()) != null) {
                                throw new IllegalArgumentException("Username already taken.");
                        }

                        // Validate required fields
                        if (renter.getUsername() == null || renter.getUsername().isEmpty()) {
                                throw new IllegalArgumentException("Username is required.");
                        }
                        if (renter.getPassword() == null || renter.getPassword().isEmpty()) {
                                throw new IllegalArgumentException("Password is required.");
                        }
                        if (renter.getEmail() == null || renter.getEmail().isEmpty()) {
                                throw new IllegalArgumentException("Email is required.");
                        }

                        // Encode password and save the renter
                        renter.setPassword(passwordEncoder.encode(renter.getPassword()));
                        return renterRepository.save(renter);

                } else {
                        throw new IllegalArgumentException("Invalid user type. Must be Artist or Renter.");
                }
        }

        // Refactored method to find a user by username (works for both Artist and Renter)
        public Object findByUsername(String username) {
                if (username == null || username.trim().isEmpty()) {
                        return null;
                }

                Artist artist = artistRepository.findByUsername(username);
                if (artist != null) {
                        return artist;
                }
                return renterRepository.findByUsername(username);
        }

        // Method to get all artists
        public List<Artist> getAllArtists() {
                return artistRepository.findAll();
        }

        // Method to get all renters
        public List<Renter> getAllRenters() {
                return renterRepository.findAll();
        }
}
