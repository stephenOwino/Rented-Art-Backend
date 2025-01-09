package com.stephenowino.Rented_Art_Backend.Controller;

import com.stephenowino.Rented_Art_Backend.Entity.Rental;
import com.stephenowino.Rented_Art_Backend.Entity.User;
import com.stephenowino.Rented_Art_Backend.Service.RentalService;
import com.stephenowino.Rented_Art_Backend.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {


        private final RentalService rentalService;

        private final UserService userService;

        public RentalController(RentalService rentalService, UserService userService) {
                this.rentalService = rentalService;
                this.userService = userService;
        }

        // Rent an art piece
        @PostMapping("/rent")
        public ResponseEntity<?> rentArtPiece(@RequestParam Long artPieceId,
                                              @RequestParam Long renterId,
                                              @RequestParam int rentalDurationDays) {
                try {
                        User renter = userService.findUserById(renterId)
                                .orElseThrow(() -> new RuntimeException("Renter not found"));
                        Rental rental = rentalService.rentArtPiece(renter, artPieceId, rentalDurationDays);
                        return ResponseEntity.ok(rental);
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
                }
        }

        // Get rentals by renter
        @GetMapping("/renter/{renterId}")
        public ResponseEntity<?> getRentalsByRenter(@PathVariable Long renterId) {
                try {
                        User renter = userService.findUserById(renterId)
                                .orElseThrow(() -> new RuntimeException("Renter not found"));
                        List<Rental> rentals = rentalService.getRentalsByRenter(renter);
                        return ResponseEntity.ok(rentals);
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
                }
        }

        // Get active rentals
        @GetMapping("/active")
        public ResponseEntity<?> getActiveRentals() {
                try {
                        List<Rental> rentals = rentalService.getActiveRentals();
                        return ResponseEntity.ok(rentals);
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
                }
        }
}

