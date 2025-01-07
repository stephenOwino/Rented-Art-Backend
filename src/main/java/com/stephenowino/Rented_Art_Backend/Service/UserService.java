package com.stephenowino.Rented_Art_Backend.Service;

import com.stephenowino.Rented_Art_Backend.Entity.Artist;
import com.stephenowino.Rented_Art_Backend.Entity.Renter;
import com.stephenowino.Rented_Art_Backend.Repository.ArtistRepo;
import com.stephenowino.Rented_Art_Backend.Repository.RenterRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

        @Autowired
        private ArtistRepo artistRepository;

        @Autowired
        private RenterRepo renterRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        // Method to register a new user (either Artist or Renter)
        public Object saveUser(Object user) {
                if (user instanceof Artist) {
                        Artist artist = (Artist) user;

                        // Check if username already exists
                        if (findByUsername(artist.getUsername()) != null) {
                                throw new IllegalArgumentException("Username already taken.");
                        }

                        artist.setPassword(passwordEncoder.encode(artist.getPassword())); // Encode password
                        return artistRepository.save(artist);
                } else if (user instanceof Renter) {
                        Renter renter = (Renter) user;

                        // Check if username already exists
                        if (findByUsername(renter.getUsername()) != null) {
                                throw new IllegalArgumentException("Username already taken.");
                        }

                        renter.setPassword(passwordEncoder.encode(renter.getPassword())); // Encode password
                        return renterRepository.save(renter);
                }
                return null;
        }

        // Method to find user by username (for login purposes)
        public Object findByUsername(String username) {
                Artist artist = artistRepository.findByUsername(username);
                if (artist != null) {
                        return artist;
                }
                return renterRepository.findByUsername(username);
        }
}
