package com.stephenowino.Rented_Art_Backend.Service.Artpiece;

import com.stephenowino.Rented_Art_Backend.Dto.ArtPieceDto.ArtPieceDto;
import com.stephenowino.Rented_Art_Backend.Entity.ArtPiece;
import com.stephenowino.Rented_Art_Backend.Request.ArtPieceUpdateRequest;
import com.stephenowino.Rented_Art_Backend.Repository.ArtPieceRepository;
import com.stephenowino.Rented_Art_Backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ArtPieceService implements IArtPieceService {
        private final ArtPieceRepository artPieceRepository;

        @Override
        public ArtPiece addArtPiece(AddArtPieceRequest artPiece) {
                ArtPiece newArtPiece = new ArtPiece(
                        artPiece.getTitle(),
                        artPiece.Role.getArtist(),
                        artPiece.getPrice(),
                        artPiece.getStock(),
                        artPiece.getDescription()
                );
                return artPieceRepository.save(newArtPiece);
        }

        @Override
        public ArtPiece getArtPieceById(Long id) {
                return artPieceRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("ArtPiece not found with id " + id));
        }

        @Override
        public void deleteArtPieceById(Long id) {
                ArtPiece artPiece = getArtPieceById(id);
                artPieceRepository.delete(artPiece);
        }

        @Override
        public ArtPiece updateArtPiece(ArtPieceUpdateRequest request, Long artPieceId) {
                ArtPiece artPiece = getArtPieceById(artPieceId);
                artPiece.setName(request.getName());
                artPiece.setArtist(request.getArtist());
                artPiece.setPrice(request.getPrice());
                artPiece.setDescription(request.getDescription());
                return artPieceRepository.save(artPiece);
        }

        @Override
        public List<ArtPiece> getAllArtPieces() {
                return artPieceRepository.findAll();
        }
//
//        @Override
//        public List<ArtPiece> getArtPiecesByCategory(String category) {
//                return List.of();
//        }

//        @Override
//        public List<ArtPiece> getArtPiecesByCategory(String category) {
//                // Assuming there's a method to filter by category in the repository
//                return artPieceRepository.findByCategory(category);
//        }

        @Override
        public List<ArtPiece> getArtPiecesByArtist(String artist) {
                return artPieceRepository.findByArtist(artist);
        }

//        @Override
//        public List<ArtPiece> getArtPiecesByCategoryAndArtist(String category, String artist) {
//                return artPieceRepository.findByCategoryAndArtist(category, artist);
//        }

        @Override
        public List<ArtPiece> getArtPiecesByName(String name) {
                return artPieceRepository.findByName(name);
        }

        @Override
        public List<ArtPiece> getArtPiecesByArtistAndName(String artist, String name) {
                return artPieceRepository.findByArtistAndName(artist, name);
        }

        @Override
        public Long countArtPiecesByArtistAndName(String artist, String name) {
                return artPieceRepository.countByArtistAndName(artist, name);
        }

        @Override
        public List<ArtPieceDto> getConvertedArtPieces(List<ArtPiece> artPieces) {
                return artPieces.stream()
                        .map(this::convertToDto)
                        .collect(Collectors.toList());
        }

        @Override
        public ArtPieceDto convertToDto(ArtPiece artPiece) {
                ArtPieceDto artPieceDto = new ArtPieceDto();
                artPieceDto.setId(artPiece.getId());
                artPieceDto.setTitle(artPiece.getName());  // Mapping 'name' to 'title' as per your DTO
                artPieceDto.setArtist(artPiece.getArtist());
                artPieceDto.setPrice(artPiece.getPrice());
                artPieceDto.setStock(artPiece.getQuantity());  // Mapping 'quantity' to 'stock'
                artPieceDto.setDescription(artPiece.getDescription());
                // Assuming ArtPieceImageDto mapping logic is implemented in ArtPieceImageDto
                // artPieceDto.setImages(artPiece.getImages().stream().map(image -> image.toDto()).collect(Collectors.toList()));
                return artPieceDto;
        }

        @Override
        public void rentArtPiece(Long artPieceId) {

        }
}
