package com.stephenowino.Rented_Art_Backend;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.stephenowino.Rented_Art_Backend.Repository.UserRepository;
import com.stephenowino.Rented_Art_Backend.Entity.User;

import java.util.Arrays;
import java.util.Collection;

@Service
public class CustomUserDetailsService implements UserDetailsService {

        private final UserRepository userRepository;

        public CustomUserDetailsService(UserRepository userRepository) {
                this.userRepository = userRepository;
        }

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                // Find the user by username (email or another unique identifier)
                User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Username not found"));

                // Return a CustomUserDetails object with the user's credentials and role
                return new CustomUserDetails(user.getEmail(), user.getPassword(), authorities(user.getRole().name()));
        }

        // Dynamically assign authorities based on the user's role
        public Collection<? extends GrantedAuthority> authorities(String role) {
                return Arrays.asList(new SimpleGrantedAuthority("ROLE_" + role));
        }
}
