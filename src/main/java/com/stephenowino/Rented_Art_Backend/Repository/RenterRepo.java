package com.stephenowino.Rented_Art_Backend.Repository;

import com.stephenowino.Rented_Art_Backend.Entity.Renter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RenterRepo extends JpaRepository<Renter, Long> {
        Renter findByUsername(String username);
}
