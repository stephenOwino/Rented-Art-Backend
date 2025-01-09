package com.stephenowino.Rented_Art_Backend.Service;

import com.stephenowino.Rented_Art_Backend.Entity.ArtPiece;
import com.stephenowino.Rented_Art_Backend.Entity.User;
import com.stephenowino.Rented_Art_Backend.Repository.ArtPieceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtPieceService {

        @Autowired
        private ArtPieceRepository artPieceRepository;

        // Add a new art piece
        public ArtPiece addArtPiece(String title, String description, String imageUrl, Double price, User artist) {
                ArtPiece artPiece = ArtPiece.builder()
                        .title(title)
                        .description(description)
                        .imageUrl(imageUrl)
                        .price(price)
                        .artist(artist)
                        .availabilityStatus(ArtPiece.ArtStatus.AVAILABLE)
                        .build();

                return artPieceRepository.save(artPiece);
        }

        // Get all available art pieces
        public List<ArtPiece> getAvailableArtPieces() {
                return artPieceRepository.findByAvailabilityStatus(ArtPiece.ArtStatus.AVAILABLE);
        }

        // Get all art pieces by artist
        public List<ArtPiece> getArtPiecesByArtist(User artist) {
                return artPieceRepository.findByArtist(artist);
        }

        // Get an art piece by ID
        public Optional<ArtPiece> getArtPieceById(Long id) {
                return artPieceRepository.findById(id);
        }

        // Mark an art piece as rented (update status)
        public ArtPiece rentArtPiece(Long id) {
                Optional<ArtPiece> artPieceOpt = artPieceRepository.findById(id);
                if (artPieceOpt.isEmpty()) {
                        throw new RuntimeException("Art piece not found");
                }

                ArtPiece artPiece = artPieceOpt.get();
                artPiece.setAvailabilityStatus(ArtPiece.ArtStatus.RENTED);
                return artPieceRepository.save(artPiece);
        }
}
