package com.stephenowino.Rented_Art_Backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ArtPiece {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String name;
        private String artist;
        private BigDecimal price;
        private int quantity;
        private String description;

        // Removed the Category relationship

        @OneToMany(mappedBy = "artPiece", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<ArtPieceImage> images;

        public ArtPiece(String name, String artist, BigDecimal price, int quantity, String description) {
                this.name = name;
                this.artist = artist;
                this.price = price;
                this.quantity = quantity;
                this.description = description;
        }

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
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

        public int getQuantity() {
                return quantity;
        }

        public void setQuantity(int quantity) {
                this.quantity = quantity;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public List<ArtPieceImage> getImages() {
                return images;
        }

        public void setImages(List<ArtPieceImage> images) {
                this.images = images;
        }

        public boolean getAvailabilityStatus() {
                return true;
        }
}

