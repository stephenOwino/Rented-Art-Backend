package com.stephenowino.Rented_Art_Backend.Dto.ArtPieceDto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ArtPieceDto {
        private Long id;
        private String title; // Renamed from "name" to better fit an art piece context
        private String artist; // Renamed from "brand" to "artist"
        private BigDecimal price;
        private int stock; // Renamed from "inventory" to "stock" for better context
        private String description;

        private List<ArtPieceImageDto> images; // Updated to use ArtPieceImageDto for consistency


        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getArtist() {
                return artist;
        }

        public void setArtist(String artist) {
                this.artist = artist;
        }

        public BigDecimal getPrice() {
                return price;
        }

        public void setPrice(BigDecimal price) {
                this.price = price;
        }

        public int getStock() {
                return stock;
        }

        public void setStock(int stock) {
                this.stock = stock;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public List<ArtPieceImageDto> getImages() {
                return images;
        }

        public void setImages(List<ArtPieceImageDto> images) {
                this.images = images;
        }
}
