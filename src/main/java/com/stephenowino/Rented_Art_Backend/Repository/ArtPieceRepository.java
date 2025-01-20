package com.stephenowino.Rented_Art_Backend.Repository;

import com.stephenowino.Rented_Art_Backend.Entity.ArtPiece;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtPieceRepository extends JpaRepository<ArtPiece, Long> {

        // Custom query to find available art pieces
        List<ArtPiece> findByAvailabilityStatus(ArtPiece status);

        // Find art pieces by artist (User)
        List<ArtPiece> findByArtist(String artist);

        // Custom query to find art pieces by product id (if relevant in your context)
        List<ArtPiece> findByProductId(Long productId); // If ArtPiece is associated with Product, adjust accordingly

        List<ArtPiece> findByCategoryAndArtist(String category, String artist);

        List<ArtPiece> findByName(String name);

        List<ArtPiece> findByArtistAndName(String artist, String name);

        Long countByArtistAndName(String artist, String name);
}

