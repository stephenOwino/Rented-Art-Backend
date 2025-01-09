package com.stephenowino.Rented_Art_Backend.Repository;

import com.stephenowino.Rented_Art_Backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

        // Custom query to find a user by email (for login)
        Optional<User> findByEmail(String email);
}

