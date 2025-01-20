package com.stephenowino.Rented_Art_Backend.Repository;

import com.stephenowino.Rented_Art_Backend.Entity.ArtPiece;
import com.stephenowino.Rented_Art_Backend.Entity.Rental;
import com.stephenowino.Rented_Art_Backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

        // Find all rentals by a specific renter
        List<Rental> findByRenter(User renter);

        // Find all rentals for a specific art piece
        List<Rental> findByArtPiece(ArtPiece artPiece);

        // Find all active rentals
        List<Rental> findByStatus(Rental.RentalStatus status);
}
