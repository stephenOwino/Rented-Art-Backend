package com.stephenowino.Rented_Art_Backend.Entity;

import com.stephenowino.Rented_Art_Backend.Entity.User;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "art_pieces")
public class ArtPiece {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;
        private String description;
        private String imageUrl;
        private Double price;
        private Date createdAt;

        @ManyToOne
        @JoinColumn(name = "artist_id")
        private User artist;

        @Enumerated(EnumType.STRING)
        private ArtStatus availabilityStatus;

        // Getters and Setters
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

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public String getImageUrl() {
                return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
                this.imageUrl = imageUrl;
        }

        public Double getPrice() {
                return price;
        }

        public void setPrice(Double price) {
                this.price = price;
        }

        public Date getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(Date createdAt) {
                this.createdAt = createdAt;
        }

        public User getArtist() {
                return artist;
        }

        public void setArtist(User artist) {
                this.artist = artist;
        }

        public ArtStatus getAvailabilityStatus() {
                return availabilityStatus;
        }

        public void setAvailabilityStatus(ArtStatus availabilityStatus) {
                this.availabilityStatus = availabilityStatus;
        }

        // Enum for Art Status
        public enum ArtStatus {
                AVAILABLE,
                RENTED
        }

        // Builder Pattern
        public static ArtPieceBuilder builder() {
                return new ArtPieceBuilder();
        }

        public static class ArtPieceBuilder {
                private Long id;
                private String title;
                private String description;
                private String imageUrl;
                private Double price;
                private Date createdAt;
                private User artist;
                private ArtStatus availabilityStatus;

                public ArtPieceBuilder id(Long id) {
                        this.id = id;
                        return this;
                }

                public ArtPieceBuilder title(String title) {
                        this.title = title;
                        return this;
                }

                public ArtPieceBuilder description(String description) {
                        this.description = description;
                        return this;
                }

                public ArtPieceBuilder imageUrl(String imageUrl) {
                        this.imageUrl = imageUrl;
                        return this;
                }

                public ArtPieceBuilder price(Double price) {
                        this.price = price;
                        return this;
                }

                public ArtPieceBuilder createdAt(Date createdAt) {
                        this.createdAt = createdAt;
                        return this;
                }

                public ArtPieceBuilder artist(User artist) {
                        this.artist = artist;
                        return this;
                }

                public ArtPieceBuilder availabilityStatus(ArtStatus availabilityStatus) {
                        this.availabilityStatus = availabilityStatus;
                        return this;
                }

                public ArtPiece build() {
                        ArtPiece artPiece = new ArtPiece();
                        artPiece.id = this.id;
                        artPiece.title = this.title;
                        artPiece.description = this.description;
                        artPiece.imageUrl = this.imageUrl;
                        artPiece.price = this.price;
                        artPiece.createdAt = this.createdAt;
                        artPiece.artist = this.artist;
                        artPiece.availabilityStatus = this.availabilityStatus;
                        return artPiece;
                }
        }
}
