package com.stephenowino.Rented_Art_Backend.Service;

import com.stephenowino.Rented_Art_Backend.Entity.ArtPiece;
import com.stephenowino.Rented_Art_Backend.Entity.Rental;
import com.stephenowino.Rented_Art_Backend.Entity.User;
import com.stephenowino.Rented_Art_Backend.Repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

        @Autowired
        private RentalRepository rentalRepository;

        @Autowired
        private ArtPieceService artPieceService;

        // Rent an art piece
        public Rental rentArtPiece(User renter, Long artPieceId, int rentalDurationDays) {
                Optional<ArtPiece> artPieceOpt = artPieceService.getArtPieceById(artPieceId);
                if (artPieceOpt.isEmpty()) {
                        throw new RuntimeException("Art piece not found");
                }

                ArtPiece artPiece = artPieceOpt.get();
                if (artPiece.getAvailabilityStatus() == ArtPiece.ArtStatus.RENTED) {
                        throw new RuntimeException("Art piece is already rented");
                }

                double totalPrice = artPiece.getPrice() * rentalDurationDays;

                Rental rental = Rental.builder()
                        .startDate(new java.util.Date())
                        .endDate(new java.util.Date(System.currentTimeMillis() + (long) rentalDurationDays * 86400000)) // Add duration in milliseconds
                        .totalPrice(totalPrice)
                        .artPiece(artPiece)
                        .renter(renter)
                        .status(Rental.RentalStatus.ACTIVE)
                        .build();

                // Mark the art piece as rented
                artPieceService.rentArtPiece(artPieceId);

                return rentalRepository.save(rental);
        }

        // Get rentals by renter
        public List<Rental> getRentalsByRenter(User renter) {
                return rentalRepository.findByRenter(renter);
        }

        // Get active rentals by status
        public List<Rental> getActiveRentals() {
                return rentalRepository.findByStatus(Rental.RentalStatus.ACTIVE);
        }
}

