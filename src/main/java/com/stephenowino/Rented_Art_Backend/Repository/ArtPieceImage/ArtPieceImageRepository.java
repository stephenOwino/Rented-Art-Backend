package com.stephenowino.Rented_Art_Backend.Repository.ArtPieceImage;



import com.stephenowino.Rented_Art_Backend.Entity.ArtPiece;
import com.stephenowino.Rented_Art_Backend.Entity.ArtPieceImage;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArtPieceImageRepository extends JpaRepository<ArtPieceImage, Long> {
        List<ArtPieceImage> findByArtPieceId(Long artPieceId);

        ArtPiece getArtPieceById(Long artPieceId);
}

