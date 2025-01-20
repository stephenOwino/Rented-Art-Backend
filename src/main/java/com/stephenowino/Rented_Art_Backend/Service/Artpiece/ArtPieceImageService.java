package com.stephenowino.Rented_Art_Backend.Service.Artpiece;


import com.stephenowino.Rented_Art_Backend.Dto.ArtPieceDto.ArtPieceImageDto;
import com.stephenowino.Rented_Art_Backend.Entity.ArtPiece;
import com.stephenowino.Rented_Art_Backend.Entity.ArtPieceImage;
import com.stephenowino.Rented_Art_Backend.Repository.ArtPieceImage.ArtPieceImageRepository;
import com.stephenowino.Rented_Art_Backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArtPieceImageService implements IArtPieceImageService {

        private final ArtPieceImageRepository artPieceImageRepository;
//        private final ArtPieceService artPieceService;

        @Override
        public ArtPieceImage getImageById(Long id) {
                return artPieceImageRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + id));
        }

        @Override
        public void deleteImageById(Long id) {
                artPieceImageRepository.findById(id).ifPresentOrElse(artPieceImageRepository::delete, () -> {
                        throw new ResourceNotFoundException("No image found with id: " + id);
                });
        }

        @Override
        public List<ArtPieceImageDto> saveImages(Long artPieceId, List<MultipartFile> files) {
                ArtPiece artPiece = artPieceImageRepository.getArtPieceById(artPieceId);

                List<ArtPieceImageDto> savedImageDto = new ArrayList<>();
                for (MultipartFile file : files) {
                        try {
                                ArtPieceImage artPieceImage = new ArtPieceImage();
                                artPieceImage.setFileName(file.getOriginalFilename());
                                artPieceImage.setFileType(file.getContentType());
                                artPieceImage.setImage(new SerialBlob(file.getBytes()));
                                artPieceImage.setArtPiece(artPiece);

                                String buildDownloadUrl = "/api/v1/images/artpiece/download/";
                                String downloadUrl = buildDownloadUrl + artPieceImage.getId();
                                artPieceImage.setDownloadUrl(downloadUrl);

                                ArtPieceImage savedArtPieceImage = artPieceImageRepository.save(artPieceImage);
                                savedArtPieceImage.setDownloadUrl(buildDownloadUrl + savedArtPieceImage.getId());
                                artPieceImageRepository.save(savedArtPieceImage);

                                ArtPieceImageDto imageDto = new ArtPieceImageDto();
                                imageDto.setId(savedArtPieceImage.getId());
                                imageDto.setFileName(savedArtPieceImage.getFileName());
                                imageDto.setDownloadUrl(savedArtPieceImage.getDownloadUrl());
                                savedImageDto.add(imageDto);
                        } catch (IOException | SQLException e) {
                                throw new RuntimeException(e.getMessage());
                        }
                }
                return savedImageDto;
        }

        @Override
        public void updateImage(MultipartFile file, Long imageId) {
                ArtPieceImage artPieceImage = getImageById(imageId);
                try {
                        artPieceImage.setFileName(file.getOriginalFilename());
                        artPieceImage.setFileType(file.getContentType());
                        artPieceImage.setImage(new SerialBlob(file.getBytes()));
                        artPieceImageRepository.save(artPieceImage);
                } catch (IOException | SQLException e) {
                        throw new RuntimeException(e.getMessage());
                }
        }
}

