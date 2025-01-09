package com.stephenowino.Rented_Art_Backend.Repository;


import com.stephenowino.Rented_Art_Backend.Entity.ArtPiece;
import com.stephenowino.Rented_Art_Backend.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ArtPieceRepository extends JpaRepository<ArtPiece, Long> {

        // Custom query to find available art pieces
        List<ArtPiece> findByAvailabilityStatus(ArtPiece.ArtStatus status);

        List<ArtPiece> findByArtist(User artist);
}


