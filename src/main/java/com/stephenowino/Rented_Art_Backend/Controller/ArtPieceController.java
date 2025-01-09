package com.stephenowino.Rented_Art_Backend.Controller;

import com.stephenowino.Rented_Art_Backend.Entity.ArtPiece;
import com.stephenowino.Rented_Art_Backend.Entity.User;
import com.stephenowino.Rented_Art_Backend.Service.ArtPieceService;
import com.stephenowino.Rented_Art_Backend.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artpieces")
public class ArtPieceController {


        private final ArtPieceService artPieceService;

        private final UserService userService;

        public ArtPieceController(ArtPieceService artPieceService, UserService userService) {
                this.artPieceService = artPieceService;
                this.userService = userService;
        }

        // Create a new art piece
        @PostMapping("/create")
        public ResponseEntity<?> createArtPiece(@RequestParam String title,
                                                @RequestParam String description,
                                                @RequestParam String imageUrl,
                                                @RequestParam Double price,
                                                @RequestParam Long artistId) {
                try {
                        User artist = userService.findUserById(artistId)
                                .orElseThrow(() -> new RuntimeException("Artist not found"));
                        ArtPiece newArtPiece = artPieceService.addArtPiece(title, description, imageUrl, price, artist);
                        return ResponseEntity.ok(newArtPiece);
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
                }
        }

        // Get all available art pieces
        @GetMapping("/available")
        public ResponseEntity<?> getAvailableArtPieces() {
                try {
                        List<ArtPiece> artPieces = artPieceService.getAvailableArtPieces();
                        return ResponseEntity.ok(artPieces);
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
                }
        }

        // Get art pieces by artist
        @GetMapping("/artist/{artistId}")
        public ResponseEntity<?> getArtPiecesByArtist(@PathVariable Long artistId) {
                try {
                        User artist = userService.findUserById(artistId)
                                .orElseThrow(() -> new RuntimeException("Artist not found"));
                        List<ArtPiece> artPieces = artPieceService.getArtPiecesByArtist(artist);
                        return ResponseEntity.ok(artPieces);
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
                }
        }

        // Rent an art piece
        @PostMapping("/rent/{artPieceId}")
        public ResponseEntity<?> rentArtPiece(@PathVariable Long artPieceId) {
                try {
                        ArtPiece rentedArtPiece = artPieceService.rentArtPiece(artPieceId);
                        return ResponseEntity.ok(rentedArtPiece);
                } catch (Exception e) {
                        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
                }
        }
}
