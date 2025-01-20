package com.stephenowino.Rented_Art_Backend.Repository.ArtPieceImage;

import com.stephenowino.Rented_Art_Backend.Entity.ArtPiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtPieceRepository extends JpaRepository<ArtPiece, Long> {

//        List<ArtPiece> findByCategoryName(String category);

        List<ArtPiece> findByArtist(String artist); // Renamed from 'brand' to 'artist'

//        List<ArtPiece> findByCategoryNameAndArtist(String category, String artist); // Adjusted for 'artist'

        List<ArtPiece> findByTitle(String title); // Renamed from 'name' to 'title'

        List<ArtPiece> findByArtistAndTitle(String artist, String title); // Adjusted for 'artist' and 'title'

        Long countByArtistAndTitle(String artist, String title); // Adjusted for 'artist' and 'title'

        Long countByArtistAndName(String artist, String name);
}

