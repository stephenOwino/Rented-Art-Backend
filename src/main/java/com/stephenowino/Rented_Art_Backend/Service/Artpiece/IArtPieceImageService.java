package com.stephenowino.Rented_Art_Backend.Service.Artpiece;

import com.stephenowino.Rented_Art_Backend.Dto.ArtPieceDto.ArtPieceImageDto;
import com.stephenowino.Rented_Art_Backend.Entity.ArtPieceImage;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IArtPieceImageService {
        ArtPieceImage getImageById(Long id);
        void deleteImageById(Long id);
        List<ArtPieceImageDto> saveImages(Long artPieceId, List<MultipartFile> files);
        void updateImage(MultipartFile file, Long imageId);
}
