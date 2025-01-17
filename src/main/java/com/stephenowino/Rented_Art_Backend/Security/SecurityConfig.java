package com.stephenowino.Rented_Art_Backend.Security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.stephenowino.Rented_Art_Backend.CustomUserDetailsService; // Ensure this import is added

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

        @Autowired
        private CustomUserDetailsService userDetailsService; // Inject CustomUserDetailsService directly

        // PasswordEncoder Bean
        @Bean
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder(12); // Strong encryption
        }

        // AuthenticationProvider Bean
        @Bean
        public AuthenticationProvider authenticationProvider() {
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                provider.setPasswordEncoder(passwordEncoder());
                provider.setUserDetailsService(userDetailsService); // Set the CustomUserDetailsService
                return provider;
        }

        // AuthenticationManager Bean
        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

        // SecurityFilterChain Bean
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                        .csrf(csrf -> csrf.disable()) // Disable CSRF for REST APIs
                        .authorizeHttpRequests(auth -> auth
                                .requestMatchers("/api/users/register/**", "/api/users/login","/api/users/logout") // Public homepage, registration, and login endpoints
                                .permitAll()
                                .requestMatchers("/api/users/profile") // Profile endpoint: only accessible by authenticated users
                                .authenticated()
                                .requestMatchers("/api/artpieces/**") // Art pieces endpoints: only accessible by authenticated users
                                .hasAnyRole("ARTIST", "RENTER") // Only ARTIST or RENTER can access art pieces
                                .anyRequest()
                                .authenticated()) // Secure all other endpoints
                        .httpBasic(Customizer.withDefaults()) // Basic authentication for now
                        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)) // Use stateful sessions for this demo
                        .build();
        }

        // CORS Configuration for allowed origins
        @Override
        public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("https://rented-art.onrender.com") // Your frontend URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedHeaders("*")
                        .allowCredentials(true); // Allow credentials like cookies
        }
}
