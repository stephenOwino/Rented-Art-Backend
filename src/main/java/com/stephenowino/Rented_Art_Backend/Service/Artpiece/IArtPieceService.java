package com.stephenowino.Rented_Art_Backend.Service.Artpiece;


import com.stephenowino.Rented_Art_Backend.Dto.ArtPieceDto.ArtPieceDto;
import com.stephenowino.Rented_Art_Backend.Entity.ArtPiece;
import com.stephenowino.Rented_Art_Backend.Request.ArtPieceUpdateRequest;

import java.util.List;

public interface IArtPieceService {
        ArtPiece addArtPiece(AddArtPieceRequest artPiece);
        ArtPiece getArtPieceById(Long id);
        void deleteArtPieceById(Long id);
        ArtPiece updateArtPiece(ArtPieceUpdateRequest artPiece, Long artPieceId);
        List<ArtPiece> getAllArtPieces();
//        List<ArtPiece> getArtPiecesByCategory(String category);
        List<ArtPiece> getArtPiecesByArtist(String artist);
//        List<ArtPiece> getArtPiecesByCategoryAndArtist(String category, String artist);
        List<ArtPiece> getArtPiecesByName(String name);
        List<ArtPiece> getArtPiecesByArtistAndName(String artist, String name);
        Long countArtPiecesByArtistAndName(String artist, String name);

        List<ArtPieceDto> getConvertedArtPieces(List<ArtPiece> artPieces);

        ArtPieceDto convertToDto(ArtPiece artPiece);

        void rentArtPiece(Long artPieceId);
}

