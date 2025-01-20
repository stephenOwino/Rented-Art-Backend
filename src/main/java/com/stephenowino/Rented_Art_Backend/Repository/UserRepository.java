package com.stephenowino.Rented_Art_Backend.Repository;

import com.stephenowino.Rented_Art_Backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

        // Custom query to find a user by email (for login)
        Optional<User> findByEmail(String email);
}

