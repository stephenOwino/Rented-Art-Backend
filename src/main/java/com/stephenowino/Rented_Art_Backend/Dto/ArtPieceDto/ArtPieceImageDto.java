package com.stephenowino.Rented_Art_Backend.Dto.ArtPieceDto;

public class ArtPieceImageDto {

        private Long id;
        private String fileName;
        private String downloadUrl;

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

        public String getDownloadUrl() {
                return downloadUrl;
        }

        public void setDownloadUrl(String downloadUrl) {
                this.downloadUrl = downloadUrl;
        }
}
