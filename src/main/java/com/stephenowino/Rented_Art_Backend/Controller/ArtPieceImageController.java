package com.stephenowino.Rented_Art_Backend.Controller;

import com.stephenowino.Rented_Art_Backend.Dto.ArtPieceDto.ArtPieceImageDto;
import com.stephenowino.Rented_Art_Backend.Entity.ArtPieceImage;
import com.stephenowino.Rented_Art_Backend.Response.ApiResponse;
import com.stephenowino.Rented_Art_Backend.Service.Artpiece.IArtPieceImageService;
import com.stephenowino.Rented_Art_Backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ArtPieceImageController {
        private final IArtPieceImageService artPieceImageService;

        @PostMapping("/upload")
        public ResponseEntity<ApiResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long artPieceId) {
                try {
                        List<ArtPieceImageDto> imageDtos = artPieceImageService.saveImages(artPieceId, files);
                        return ResponseEntity.ok(new ApiResponse("Upload success!", imageDtos));
                } catch (Exception e) {
                        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload failed!", e.getMessage()));
                }
        }

        @GetMapping("/image/download/{imageId}")
        public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
                ArtPieceImage artPieceImage = artPieceImageService.getImageById(imageId);
                ByteArrayResource resource = new ByteArrayResource(artPieceImage.getImage().getBytes(1, (int) artPieceImage.getImage().length()));
                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(artPieceImage.getFileType()))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + artPieceImage.getFileName() + "\"")
                        .body(resource);
        }

        @PutMapping("/image/{imageId}/update")
        public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId, @RequestBody MultipartFile file) {
                try {
                        ArtPieceImage artPieceImage = artPieceImageService.getImageById(imageId);
                        if (artPieceImage != null) {
                                artPieceImageService.updateImage(file, imageId);
                                return ResponseEntity.ok(new ApiResponse("Update success!", null));
                        }
                } catch (ResourceNotFoundException e) {
                        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), NOT_FOUND));
                }
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update failed!", INTERNAL_SERVER_ERROR));
        }


        @DeleteMapping("/image/{imageId}/delete")
        public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId) {
                try {
                        ArtPieceImage artPieceImage = artPieceImageService.getImageById(imageId);
                        if (artPieceImage != null) {
                                artPieceImageService.deleteImageById(imageId);
                                return ResponseEntity.ok(new ApiResponse("Delete success!", null));
                        }
                } catch (ResourceNotFoundException e) {
                        return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
                }
                return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Delete failed!", INTERNAL_SERVER_ERROR));
        }
}
