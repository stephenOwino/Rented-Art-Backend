package com.stephenowino.Rented_Art_Backend.Controller;

import com.stephenowino.Rented_Art_Backend.Dto.ArtPieceDto.ArtPieceDto;
import com.stephenowino.Rented_Art_Backend.Entity.ArtPiece;
import com.stephenowino.Rented_Art_Backend.Request.ArtPieceUpdateRequest;
import com.stephenowino.Rented_Art_Backend.Response.ApiResponse;
import com.stephenowino.Rented_Art_Backend.Service.Artpiece.AddArtPieceRequest;
import com.stephenowino.Rented_Art_Backend.Service.Artpiece.IArtPieceService;
import com.stephenowino.Rented_Art_Backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/artpieces")
public class ArtPieceController {
        private final IArtPieceService artPieceService;

        @GetMapping("/all")
        public ResponseEntity<ApiResponse> getAllArtPieces() {
                List<ArtPiece> artPieces = artPieceService.getAllArtPieces();
                List<ArtPieceDto> convertedArtPieces = artPieceService.getConvertedArtPieces(artPieces);
                return ResponseEntity.ok(new ApiResponse("success", convertedArtPieces));
        }

        @GetMapping("/artpiece/{artPieceId}")
        public ResponseEntity<ApiResponse> getArtPieceById(@PathVariable Long artPieceId) {
                try {
                        ArtPiece artPiece = artPieceService.getArtPieceById(artPieceId);
                        ArtPieceDto artPieceDto = artPieceService.convertToDto(artPiece);
                        return ResponseEntity.ok(new ApiResponse("success", artPieceDto));
                } catch (ResourceNotFoundException e) {
                        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
                }
        }

        @PostMapping("/add")
        public ResponseEntity<ApiResponse> addArtPiece(@RequestBody AddArtPieceRequest artPiece) {
                try {
                        ArtPiece theArtPiece = artPieceService.addArtPiece(artPiece);
                        ArtPieceDto artPieceDto = artPieceService.convertToDto(theArtPiece);
                        return ResponseEntity.ok(new ApiResponse("Add art piece success!", artPieceDto));
                } catch (Exception e) {
                        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
                }
        }

        @PutMapping("/artpiece/{artPieceId}/update")
        public ResponseEntity<ApiResponse> updateArtPiece(@RequestBody ArtPieceUpdateRequest request, @PathVariable Long artPieceId) {
                try {
                        ArtPiece theArtPiece = artPieceService.updateArtPiece(request, artPieceId);
                        ArtPieceDto artPieceDto = artPieceService.convertToDto(theArtPiece);
                        return ResponseEntity.ok(new ApiResponse("Update art piece success!", artPieceDto));
                } catch (ResourceNotFoundException e) {
                        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
                }
        }

        @DeleteMapping("/artpiece/{artPieceId}/delete")
        public ResponseEntity<ApiResponse> deleteArtPiece(@PathVariable Long artPieceId) {
                try {
                        artPieceService.deleteArtPieceById(artPieceId);
                        return ResponseEntity.ok(new ApiResponse("Delete art piece success!", artPieceId));
                } catch (ResourceNotFoundException e) {
                        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
                }
        }

        @GetMapping("/artpiece/by-artist")
        public ResponseEntity<ApiResponse> findArtPieceByArtist(@RequestParam String artist) {
                List<ArtPiece> artPieces = artPieceService.getArtPiecesByArtist(artist);
                if (artPieces.isEmpty()) {
                        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No art pieces found for artist " + artist, null));
                }
                List<ArtPieceDto> convertedArtPieces = artPieceService.getConvertedArtPieces(artPieces);
                return ResponseEntity.ok(new ApiResponse("success", convertedArtPieces));
        }
}
