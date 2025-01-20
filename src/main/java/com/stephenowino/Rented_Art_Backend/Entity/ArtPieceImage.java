package com.stephenowino.Rented_Art_Backend.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ArtPieceImage {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String fileName;
        private String fileType;

        @Lob
        private Blob image; // The image data

        private String downloadUrl;

        @ManyToOne
        @JoinColumn(name = "art_piece_id")
        private ArtPiece artPiece;  // The art piece this image belongs to


        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getFileName() {
                return fileName;
        }

        public void setFileName(String fileName) {
                this.fileName = fileName;
        }

        public String getFileType() {
                return fileType;
        }

        public void setFileType(String fileType) {
                this.fileType = fileType;
        }

        public Blob getImage() {
                return image;
        }

        public void setImage(Blob image) {
                this.image = image;
        }

        public String getDownloadUrl() {
                return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
                this.downloadUrl = downloadUrl;
        }

        public ArtPiece getArtPiece() {
                return artPiece;
        }

        public void setArtPiece(ArtPiece artPiece) {
                this.artPiece = artPiece;
        }
}
