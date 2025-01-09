package com.stephenowino.Rented_Art_Backend.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Table(name = "art_pieces")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArtPiece {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String title;

        private String description;

        private String imageUrl; // URL to the art image

        private Double price; // Price for renting

        private Date createdAt; // Timestamp for when the art piece was created

        @ManyToOne
        @JoinColumn(name = "artist_id")
        private User artist; // Artist who created the art piece

        @Enumerated(EnumType.STRING)
        private ArtStatus availabilityStatus; // Availability status of the art piece

        public enum ArtStatus {
                AVAILABLE,
                RENTED
        }
}

