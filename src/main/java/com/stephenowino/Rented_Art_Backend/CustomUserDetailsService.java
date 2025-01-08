package com.stephenowino.Rented_Art_Backend;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.stephenowino.Rented_Art_Backend.Entity.Renter;
import com.stephenowino.Rented_Art_Backend.Repository.RenterRepo;
import com.stephenowino.Rented_Art_Backend.Entity.Artist;
import com.stephenowino.Rented_Art_Backend.Repository.ArtistRepo;

import java.util.Arrays;
import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {

        private final RenterRepo renterRepo;
        private final ArtistRepo artistRepo;

        public CustomUserDetailsService(RenterRepo renterRepo, ArtistRepo artistRepo) {
                this.renterRepo = renterRepo;
                this.artistRepo = artistRepo;
        }

        @Override
        public UserDetails loadUserByUsername(String username) {
                Renter renter = renterRepo.findByUsername(username);
                Artist artist = artistRepo.findByUsername(username);

                if (renter != null) {
                        return new CustomUserDetails(renter.getUsername(), renter.getPassword(), authorities());
                } else if (artist != null) {
                        return new CustomUserDetails(artist.getUsername(), artist.getPassword(), authorities());
                } else {
                        throw new UsernameNotFoundException("Username not found");
                }
        }

        public Collection<? extends GrantedAuthority> authorities() {
                return Arrays.asList(new SimpleGrantedAuthority("USER"));
        }
}
