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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

        @Autowired
        UserDetailsService userDetailsService;

//        @Autowired
//        private JwtFilter jwtFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                return http
                        .csrf(customizer ->customizer.disable())
                        .authorizeHttpRequests(request->request
                                .requestMatchers("register","login")
                                .permitAll()
                                .anyRequest().authenticated())
                        // http.formLogin(Customizer.withDefaults());
                        .httpBasic(Customizer.withDefaults())//to avoid returning form while testing in postman(rest clients)
                        .sessionManagement(session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))//making http stateless
                        //to generate a new session id everytime the user makes a request
                        //.addFilterBefore( jwtFilter, UsernamePasswordAuthenticationFilter.class)
                        .build();
        }
        @Bean
        public AuthenticationProvider authenticationProvider(){
                DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
                provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
                provider.setUserDetailsService(userDetailsService);

                return provider;
        }

        @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
                return config.getAuthenticationManager();
        }

        // CORS Configuration to handle cross-origin requests globally
        @Override
        public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Allow all paths
                        .allowedOrigins("https://rented-art.onrender.com") // Your hosted frontend URL
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow specific HTTP methods
                        .allowedHeaders("*") // Allow all headers
                        .allowCredentials(true); // Allow credentials
        }

}
