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

                /**
                 * Register a new user (either Artist or Renter).
                 *
                 * @param user the user object to save
                 * @return the saved user
                 * @throws IllegalArgumentException if the username already exists or the user type is invalid
                 */
                public Object saveUser(Object user) {
                        if (user == null) {
                                throw new IllegalArgumentException("User object cannot be null.");
                        }

                        if (user instanceof Artist) {
                                Artist artist = (Artist) user;

                                // Check if username already exists
                                if (findByUsername(artist.getUsername()) != null) {
                                        throw new IllegalArgumentException("Username already taken.");
                                }

                                // Encode password and save the artist
                                artist.setPassword(passwordEncoder.encode(artist.getPassword()));
                                return artistRepository.save(artist);

                        } else if (user instanceof Renter) {
                                Renter renter = (Renter) user;

                                // Check if username already exists
                                if (findByUsername(renter.getUsername()) != null) {
                                        throw new IllegalArgumentException("Username already taken.");
                                }

                                // Encode password and save the renter
                                renter.setPassword(passwordEncoder.encode(renter.getPassword()));
                                return renterRepository.save(renter);

                        } else {
                                throw new IllegalArgumentException("Invalid user type. Must be Artist or Renter.");
                        }
                }

                /**
                 * Find a user by username.
                 *
                 * @param username the username to search for
                 * @return the user object if found, otherwise null
                 */
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
        }
